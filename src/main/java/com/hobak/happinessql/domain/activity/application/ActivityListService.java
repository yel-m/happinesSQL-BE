package com.hobak.happinessql.domain.activity.application;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public ActivityListResponseDto getActivities(Long lastCategoryId, int size, Long userId) {
        User user = userFindService.findUserById(userId);
        List<CategoryDto> categoryDtos = new ArrayList<>();

        Page<Category> categories = fetchCategoryPages(lastCategoryId, size);
        for (Category category : categories.getContent()) {
            List<ActivityDto> activityDtos = fetchActivityDtosByCategory(category, user);
            CategoryDto categoryDto = CategoryDto.builder()
                    .id(category.getCategoryId())
                    .name(category.getName())
                    .activities(activityDtos)
                    .build();
            categoryDtos.add(categoryDto);

            if (activityDtos.size() < size) {
                lastCategoryId = category.getCategoryId();
                size -= activityDtos.size();
            } else {
                break;
            }
        }

        return ActivityListResponseDto.builder()
                .categories(categoryDtos)
                .build();
    }

    private Page<Category> fetchCategoryPages(Long lastCategoryId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return categoryRepository.findByCategoryIdGreaterThanOrderByCategoryIdAsc(lastCategoryId, pageRequest);
    }

    private List<ActivityDto> fetchActivityDtosByCategory(Category category, User user) {
        List<Activity> activities = activityRepository.findByCategoryAndUser(category, user);
        return activities.stream()
                .map(activity -> ActivityDto.builder()
                        .id(activity.getActivityId())
                        .name(activity.getName())
                        .emoji(activity.getEmoji())
                        .build())
                .collect(Collectors.toList());
    }
}