package com.hobak.happinessql.domain.user.api;

import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.converter.UserConverter;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.dto.UserInfoResponseDto;
import com.hobak.happinessql.global.response.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserFindService userFindService;

    @GetMapping("/profile")
    public DataResponseDto<Object> getUserInfo() {
        // TODO : 임시값 -> 로그인한 유저의 id를 찾아내는 로직으로 변경
        Long userId = 1L;

        User user = userFindService.findUserById(userId);
        UserInfoResponseDto userInfoResponseDto = UserConverter.toUserInfoResponseDto(user);

        return DataResponseDto.of(userInfoResponseDto, "유저 프로필을 성공적으로 조회했습니다.");
    }
}
