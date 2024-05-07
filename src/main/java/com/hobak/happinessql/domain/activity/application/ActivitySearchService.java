package com.hobak.happinessql.domain.activity.application;

import com.hobak.happinessql.domain.activity.converter.ActivityConverter;
import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.domain.Category;
import com.hobak.happinessql.domain.activity.dto.ActivityDto;
import com.hobak.happinessql.domain.activity.dto.ActivitySearchResponseDto;
import com.hobak.happinessql.domain.activity.dto.CategoryDto;
import com.hobak.happinessql.domain.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivitySearchService {
    private final ActivityRepository activityRepository;

    public ActivitySearchResponseDto searchActivities(String keyword, Long userId) {
        List<Activity> activitiesByCategory = activityRepository.findByCategoryNameContaining(keyword);
        List<Activity> activitiesByName = activityRepository.findByNameContaining(keyword);

        Map<Long, CategoryDto> categoryDtoMap = new HashMap<>();

        for (Activity activity : activitiesByCategory) {
            Category category = activity.getCategory();
            if(category.getUserId() == null || category.getUserId().equals(userId)){
                CategoryDto categoryDto = categoryDtoMap.getOrDefault(category.getCategoryId(), ActivityConverter.toCategoryDto(category));
                categoryDto.getActivities().add(ActivityConverter.toActivityDto(activity));
                categoryDtoMap.put(category.getCategoryId(), categoryDto);
            }
        }

        for (Activity activity : activitiesByName) {
            Category category = activity.getCategory();
            if(categoryDtoMap.containsKey(category.getCategoryId())){
                continue;
            }
            if(category.getUserId() == null || category.getUserId().equals(userId)){
                CategoryDto categoryDto = categoryDtoMap.getOrDefault(category.getCategoryId(), ActivityConverter.toCategoryDto(category));
                ActivityDto activityDto = ActivityConverter.toActivityDto(activity);
                if (!categoryDto.getActivities().contains(activityDto)) {
                    categoryDto.getActivities().add(activityDto);
                }
                categoryDtoMap.put(category.getCategoryId(), categoryDto);
            }
        }

        return ActivityConverter
                .toActivitySearchResponseDto(new ArrayList<>(categoryDtoMap.values()));
    }

}
