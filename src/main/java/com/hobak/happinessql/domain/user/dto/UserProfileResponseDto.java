package com.hobak.happinessql.domain.user.dto;

import com.hobak.happinessql.domain.user.domain.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileResponseDto {

    private Long userId;

    private String name;

    private Gender gender;

    private int age;

    @Builder
    public UserProfileResponseDto(Long userId, String name, Gender gender, int age) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
}
