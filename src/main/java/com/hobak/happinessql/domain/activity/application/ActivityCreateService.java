package com.hobak.happinessql.domain.activity.application;

import com.hobak.happinessql.domain.activity.converter.ActivityConverter;
import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.domain.Category;
import com.hobak.happinessql.domain.activity.dto.ActivityCreateRequestDto;
import com.hobak.happinessql.domain.activity.dto.ActivityResponseDto;
import com.hobak.happinessql.domain.activity.repository.ActivityRepository;
import com.hobak.happinessql.domain.activity.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ActivityCreateService {

    private final ActivityRepository activityRepository;
    private final CategoryRepository categoryRepository;
    public ActivityResponseDto createActivity(ActivityCreateRequestDto requestDto, Long userId) {
        Category category = categoryRepository.findByUserId(userId);
        Activity activity = ActivityConverter.toActivity(requestDto, category);

        Activity savedActivity = activityRepository.save(activity);
        return ActivityConverter.toActivityResponseDto(savedActivity.getActivityId());

    }
}