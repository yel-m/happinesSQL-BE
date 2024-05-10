package com.hobak.happinessql.domain.activity.converter;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.domain.Category;
import com.hobak.happinessql.domain.activity.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityConverter {

    public static Activity toActivity(ActivityCreateRequestDto requestDto, Category category) {
        return Activity.builder()
                .name(requestDto.getActivityName())
                .category(category)
                .build();
    }
    public static ActivityDto toActivityDto(Activity activity) {
        return ActivityDto.builder()
                .id(activity.getActivityId())
                .name(activity.getName())
                .emoji(activity.getEmoji())
                .build();
    }

    public static List<ActivityDto> toActivityDtoList(List<Activity> activities) {
        return activities.stream()
                .map(ActivityConverter::toActivityDto)
                .collect(Collectors.toList());
    }

    public static CategoryDto toCategoryDto(Category category,  List<ActivityDto> activityDtos) {
        return CategoryDto.builder()
                .id(category.getCategoryId())
                .name(category.getName())
                .activities(activityDtos)
                .build();
    }
    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getCategoryId())
                .name(category.getName())
                .activities(new ArrayList<>())
                .build();
    }


    public static ActivityResponseDto toActivityResponseDto(Long activityId){
        return ActivityResponseDto.builder()
                .activityId(activityId)
                .build();
    }
    public static ActivityUpdateResponseDto toActivityUpdateResponseDto(Activity activity){
        return ActivityUpdateResponseDto.builder()
                .categoryId(activity.getCategory().getCategoryId())
                .categoryName(activity.getCategory().getName())
                .activityId(activity.getActivityId())
                .activityName(activity.getName())
                .build();
    }

    public static ActivitySearchResponseDto toActivitySearchResponseDto(List<CategoryDto> categories){
        return ActivitySearchResponseDto.builder()
                .categories(categories)
                .build();
    }
}