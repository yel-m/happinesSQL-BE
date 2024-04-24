package com.hobak.happinessql.domain.user.dto;

import com.hobak.happinessql.domain.user.domain.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponseDto {
    private String name;

    private Gender gender;

    private int age;

    @Builder
    public UserInfoResponseDto(String name, Gender gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
}
