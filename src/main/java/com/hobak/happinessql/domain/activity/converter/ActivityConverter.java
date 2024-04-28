package com.hobak.happinessql.domain.activity.converter;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.domain.Category;
import com.hobak.happinessql.domain.activity.dto.ActivityDto;
import com.hobak.happinessql.domain.activity.dto.CategoryDto;

import java.util.List;
import java.util.stream.Collectors;

public class ActivityConverter {
    public static ActivityDto toActivityDto(Activity activity) {
        return ActivityDto.builder()
                .id(activity.getActivityId())
                .name(activity.getName())
                .emoji(activity.getEmoji()) // 이 부분 추가
                .build();
    }

    public static List<ActivityDto> toActivityDtoList(List<Activity> activities) {
        return activities.stream()
                .map(ActivityConverter::toActivityDto)
                .collect(Collectors.toList());
    }

    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getCategoryId())
                .name(category.getName())
                .build();
    }

    public static CategoryDto toCategoryDtoWithActivities(Category category, List<Activity> activities) {
        return CategoryDto.builder()
                .id(category.getCategoryId())
                .name(category.getName())
                .activities(toActivityDtoList(activities))
                .build();
    }
}