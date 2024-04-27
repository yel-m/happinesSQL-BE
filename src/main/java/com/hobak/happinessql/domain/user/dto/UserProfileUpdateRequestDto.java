package com.hobak.happinessql.domain.user.dto;

import com.hobak.happinessql.domain.user.domain.Gender;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileUpdateRequestDto {

    private Gender gender;

    @Min(1)
    private int age;

}

