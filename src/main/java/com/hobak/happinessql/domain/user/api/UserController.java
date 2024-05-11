package com.hobak.happinessql.domain.user.api;

import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.application.UserProfileService;
import com.hobak.happinessql.domain.user.converter.UserConverter;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.dto.UserInfoResponseDto;
import com.hobak.happinessql.domain.user.dto.UserProfileUpdateRequestDto;
import com.hobak.happinessql.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name="User", description = "유저 관련 REST API에 대한 명세를 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserFindService userFindService;
    private final UserProfileService userProfileService;

    @Operation(summary = "프로필 조회", description = "유저의 프로필을 조회합니다.")
    @GetMapping("/profile")
    public DataResponseDto<Object> getUserInfo() {
        // TODO : 임시값 -> 로그인한 유저의 id를 찾아내는 로직으로 변경
        Long userId = 1L;

        User user = userFindService.findUserById(userId);
        UserInfoResponseDto userInfoResponseDto = UserConverter.toUserInfoResponseDto(user);

        return DataResponseDto.of(userInfoResponseDto, "유저 프로필을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "프로필 수정", description = "유저의 프로필을 수정합니다. 성별과 나이만 수정이 가능합니다.")
    @PutMapping("/profile")
    public DataResponseDto<Object> updateUserInfo(@RequestBody @Valid UserProfileUpdateRequestDto requestDto) {
        Long userId = 1L;

        User user = userFindService.findUserById(userId);
        User updatedUser = userProfileService.updateUserProfile(user, requestDto);
        UserInfoResponseDto responseDto = UserConverter.toUserInfoResponseDto(updatedUser);

        return DataResponseDto.of(responseDto, "유저 프로필을 성공적으로 수정했습니다.");
    }
}
