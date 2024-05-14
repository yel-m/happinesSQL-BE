package com.hobak.happinessql.domain.activity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryResponseDto {
    private Long categoryId;
    @Builder
    CategoryResponseDto(Long categoryId){
        this.categoryId = categoryId;
    }
}
