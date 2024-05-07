package com.hobak.happinessql.domain.activity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityUpdateRequestDto {
    String name;
    @Builder
    ActivityUpdateRequestDto(String name){
        this.name = name;
    }
}
