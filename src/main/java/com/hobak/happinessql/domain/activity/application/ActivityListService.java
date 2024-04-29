package com.hobak.happinessql.domain.activity.application;

import com.hobak.happinessql.domain.activity.converter.ActivityConverter;
import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.domain.Category;
import com.hobak.happinessql.domain.activity.dto.ActivityListResponseDto;
import com.hobak.happinessql.domain.activity.dto.ActivityDto;
import com.hobak.happinessql.domain.activity.dto.CategoryDto;
import com.hobak.happinessql.domain.activity.repository.ActivityRepository;
import com.hobak.happinessql.domain.activity.repository.CategoryRepository;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.application.UserFindService;
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
    private final UserFindService userFindService;

    public ActivityListResponseDto getActivitiesByUserId(Long userId) {
        User user = userFindService.findUserById(userId);
        List<CategoryDto> categoryDtos = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            List<ActivityDto> activityDtos = fetchActivityDtosByCategory(category, user);
            CategoryDto categoryDto = ActivityConverter.toCategoryDto(category, activityDtos);
            categoryDtos.add(categoryDto);
        }

        return ActivityListResponseDto.builder()
                .categories(categoryDtos)
                .build();
    }

    private List<ActivityDto> fetchActivityDtosByCategory(Category category, User user) {
        List<Activity> activities = activityRepository.findByCategory(category);
        return activities.stream()
                .map(activity -> ActivityConverter.toActivityDto(activity))
                .collect(Collectors.toList());
    }
}