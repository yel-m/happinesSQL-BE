package com.hobak.happinessql.domain.activity.application;

import com.hobak.happinessql.domain.activity.converter.ActivityConverter;
import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.domain.Category;
import com.hobak.happinessql.domain.activity.dto.ActivityListResponseDto;
import com.hobak.happinessql.domain.activity.dto.ActivityDto;
import com.hobak.happinessql.domain.activity.dto.CategoryDto;
import com.hobak.happinessql.domain.activity.repository.ActivityRepository;
import com.hobak.happinessql.domain.activity.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivityListService {

    private final ActivityRepository activityRepository;
    private final CategoryRepository categoryRepository;

    public ActivityListResponseDto getActivities(Long userId) {
        List<CategoryDto> categoryDtos = new ArrayList<>();

        List<Category> categories = categoryRepository.findByUserIdIsNull();
        for (Category category : categories) {
            List<ActivityDto> activityDtos = fetchActivityDtosBy(category);
            CategoryDto categoryDto = ActivityConverter.toCategoryDto(category, activityDtos);
            categoryDtos.add(categoryDto);
        }
        Category etcCategory = categoryRepository.findByUserId(userId);
        List<ActivityDto> etcActivities = fetchActivityDtosBy(etcCategory);
        CategoryDto categoryDto = ActivityConverter.toCategoryDto(etcCategory, etcActivities);
        categoryDtos.add(categoryDto);
        return ActivityConverter.toActivityListResponseDto(categoryDtos);
    }

    private List<ActivityDto> fetchActivityDtosBy(Category category) {
        List<Activity> activities = activityRepository.findByCategory(category);
        return activities.stream()
                .map(activity -> ActivityConverter.toActivityDto(activity))
                .collect(Collectors.toList());
    }
}