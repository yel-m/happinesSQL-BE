package com.hobak.happinessql.domain.activity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityListResponseDto {

    private List<CategoryDto> categories;

    @Builder
    public ActivityListResponseDto(List<CategoryDto> categories) {
        this.categories = categories;
    }
}