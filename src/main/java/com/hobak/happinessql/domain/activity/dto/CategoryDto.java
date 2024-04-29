package com.hobak.happinessql.domain.activity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.domain.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryDto {

    private Long id;

    private String name;
    private List<ActivityDto> activities;

    @Builder
    public CategoryDto(Long id, String name, List<ActivityDto> activities) {
        this.id = id;
        this.name = name;
        this.activities = activities;
    }
}