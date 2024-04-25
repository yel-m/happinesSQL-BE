package com.hobak.happinessql.domain.user.converter;

import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.dto.UserInfoResponseDto;

public class UserConverter {
    public static UserInfoResponseDto toUserInfoResponseDto(User user) {
        return UserInfoResponseDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .build();
    }
}
