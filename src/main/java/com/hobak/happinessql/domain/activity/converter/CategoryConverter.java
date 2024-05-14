package com.hobak.happinessql.domain.activity.converter;

import com.hobak.happinessql.domain.activity.domain.Category;
import com.hobak.happinessql.domain.activity.dto.CategoryResponseDto;

public class CategoryConverter {
    public static Category toCategory(Long userId) {
        return Category.builder()
                .name("기타")
                .userId(userId)
                .build();
    }
    public static CategoryResponseDto toCategoryResponseDto(Long categoryId){
        return CategoryResponseDto.builder()
                .categoryId(categoryId)
                .build();
    }
}
