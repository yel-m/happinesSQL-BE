package com.hobak.happinessql.domain.activity.api;

import com.hobak.happinessql.domain.activity.application.ActivityCreateService;
import com.hobak.happinessql.domain.activity.application.ActivityDeleteService;
import com.hobak.happinessql.domain.activity.dto.ActivityCreateRequestDto;
import com.hobak.happinessql.domain.activity.dto.ActivityCreateResponseDto;
import com.hobak.happinessql.domain.activity.dto.ActivityListResponseDto;
import com.hobak.happinessql.domain.activity.application.ActivityListService;
import com.hobak.happinessql.global.response.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityListService activityListService;
    private final ActivityCreateService activityCreateService;
    private final ActivityDeleteService activityDeleteService;

    @GetMapping
    public DataResponseDto<ActivityListResponseDto> getActivitiesByUserId(@RequestParam Long userId) {
        ActivityListResponseDto response = activityListService.getActivitiesByUserId(userId);
        return DataResponseDto.of(response, "사용자의 모든 카테고리별 활동을 성공적으로 조회했습니다.");
    }
    @PostMapping
    public DataResponseDto<ActivityCreateResponseDto> createActivity(@RequestBody ActivityCreateRequestDto request, @RequestParam Long userId){
        ActivityCreateResponseDto response = activityCreateService.createActivity(request,userId);
        return DataResponseDto.of(response,"활동을 성공적으로 추가했습니다.");
    }
    @DeleteMapping("/{activityId}")
    public Long deleteActivity(@PathVariable Long activityId){
        activityDeleteService.deleteActivity(activityId);
        return activityId;
    }
}