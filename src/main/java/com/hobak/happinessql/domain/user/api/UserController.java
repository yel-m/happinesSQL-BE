package com.hobak.happinessql.domain.user.api;

import com.hobak.happinessql.domain.user.application.CustomUserDetailsService;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.application.UserProfileService;
import com.hobak.happinessql.domain.user.application.UserService;
import com.hobak.happinessql.domain.user.converter.UserConverter;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.dto.*;
import com.hobak.happinessql.global.auth.JwtToken;
import com.hobak.happinessql.global.response.DataResponseDto;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserFindService userFindService;
    private final UserProfileService userProfileService;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    @NonNull
    private PasswordEncoder passwordEncoder;
    @GetMapping("/profile")
    public DataResponseDto<Object> getUserInfo() {
        // TODO : 임시값 -> 로그인한 유저의 id를 찾아내는 로직으로 변경
        Long userId = 1L;

        User user = userFindService.findUserById(userId);
        UserInfoResponseDto userInfoResponseDto = UserConverter.toUserInfoResponseDto(user);

        return DataResponseDto.of(userInfoResponseDto, "유저 프로필을 성공적으로 조회했습니다.");
    }

    @PutMapping("/profile")
    public DataResponseDto<Object> updateUserInfo(@RequestBody @Valid UserProfileUpdateRequestDto requestDto) {
        Long userId = 1L;

        User user = userFindService.findUserById(userId);
        User updatedUser = userProfileService.updateUserProfile(user, requestDto);
        UserInfoResponseDto responseDto = UserConverter.toUserInfoResponseDto(updatedUser);

        return DataResponseDto.of(responseDto, "유저 프로필을 성공적으로 수정했습니다.");
    }
    @PostMapping("/login")
    public DataResponseDto<JwtToken> signIn(@RequestBody SignInDto signInDto) {
        UserDetails user = customUserDetailsService.loadUserByUsername(signInDto.getUsername());
        if(passwordEncoder.matches(signInDto.getPassword(), user.getPassword())){
            JwtToken jwtToken = userService.signIn(signInDto.getUsername(), signInDto.getPassword());
            return DataResponseDto.of(jwtToken,"로그인이 정상적으로 처리되었습니다.");
        }
        else return DataResponseDto.of(null,"비밀번호가 틀렸습니다.");

    }

    @PostMapping("/test")
    public String test(){
        return "success";
    }
    @PostMapping("/sign-up")
    public DataResponseDto<Object> signUp(@RequestBody SignUpDto signUpDto){
        UserDto savedUserDto = userService.signUp(signUpDto);
        return DataResponseDto.of("회원가입이 성공적으로 처리되었습니다.");
    }
}

