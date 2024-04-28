package com.hobak.happinessql.domain.activity.api;

import com.hobak.happinessql.domain.activity.dto.ActivityListResponseDto;
import com.hobak.happinessql.domain.activity.application.ActivityListService;
import com.hobak.happinessql.global.response.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityListService activityListService;

    @GetMapping
    public DataResponseDto<Object> getActivities(
            @RequestParam(value = "lastCategoryId", defaultValue = "0") Long lastCategoryId,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "userId") Long userId) {
        ActivityListResponseDto response = activityListService.getActivities(lastCategoryId, size, userId);
        return DataResponseDto.of(response, "활동 목록을 성공적으로 조회했습니다.");
    }
}