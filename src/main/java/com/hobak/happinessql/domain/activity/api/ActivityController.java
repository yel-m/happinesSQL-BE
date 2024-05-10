package com.hobak.happinessql.domain.activity.api;

import com.hobak.happinessql.domain.activity.application.*;
import com.hobak.happinessql.domain.activity.dto.*;
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
    private final ActivityUpdateService activityUpdateService;
    private final ActivitySearchService activitySearchService;
    @GetMapping
    public DataResponseDto<ActivityListResponseDto> getActivitiesByUserId(@RequestParam Long userId) {
        ActivityListResponseDto response = activityListService.getActivities(userId);
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
    @PutMapping("/{activityId}")
    public DataResponseDto<ActivityUpdateResponseDto> updateActicity(@PathVariable Long activityId, @RequestBody ActivityUpdateRequestDto requestDto){
        ActivityUpdateResponseDto responseDto = activityUpdateService.updateActivity(activityId,requestDto);
        return DataResponseDto.of(responseDto,"활동을 성공적으로 수정했습니다.");
    }
    @PostMapping("/search")
    public DataResponseDto<ActivitySearchResponseDto> searchActivities(@RequestBody ActivitySearchRequestDto requestDto, @RequestParam Long userId) {
        ActivitySearchResponseDto responseDto = activitySearchService.searchActivities(requestDto.getSearch(), userId);
        return DataResponseDto.of(responseDto, "활동을 성공적으로 검색했습니다.");
    }
}