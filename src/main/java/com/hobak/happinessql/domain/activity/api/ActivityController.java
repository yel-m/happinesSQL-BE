package com.hobak.happinessql.domain.activity.api;

import com.hobak.happinessql.domain.activity.application.*;
import com.hobak.happinessql.domain.activity.converter.ActivityConverter;
import com.hobak.happinessql.domain.activity.dto.*;
import com.hobak.happinessql.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name="Activity", description = "활동 관련 REST API에 대한 명세를 제공합니다.")
@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityListService activityListService;
    private final ActivityCreateService activityCreateService;
    private final ActivityDeleteService activityDeleteService;
    private final ActivityUpdateService activityUpdateService;
    private final ActivitySearchService activitySearchService;

    @Operation(summary = "활동 리스트 조회", description = "유저가 가지고 있는 활동 목록을 조회합니다. 행복 기록 생성 시 활동 선택 창에서 사용하는 API입니다.")
    @GetMapping
    public DataResponseDto<ActivityListResponseDto> getActivities(@RequestParam Long userId) {
        ActivityListResponseDto response = activityListService.getActivities(userId);
        return DataResponseDto.of(response, "사용자의 모든 카테고리별 활동을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "활동 추가", description = "기타 카테고리에 활동을 추가합니다.")
    @PostMapping
    public DataResponseDto<ActivityResponseDto> createActivity(@RequestBody ActivityCreateRequestDto requestDto, @RequestParam Long userId){
        ActivityResponseDto responseDto = activityCreateService.createActivity(requestDto,userId);
        return DataResponseDto.of(responseDto,"활동을 성공적으로 추가했습니다.");
    }

    @Operation(summary = "활동 삭제", description = "기타 카테고리에 있는 활동을 삭제합니다.")
    @DeleteMapping("/{activityId}")
    public DataResponseDto<ActivityResponseDto> deleteActivity(@PathVariable Long activityId){
        activityDeleteService.deleteActivity(activityId);
        ActivityResponseDto responseDto = ActivityConverter.toActivityResponseDto(activityId);
        return DataResponseDto.of(responseDto, "활동을 성공적으로 삭제했습니다.");
    }

    @Operation(summary = "활동 수정", description = "기타 카테고리에 있는 활동을 수정합니다.")
    @PutMapping("/{activityId}")
    public DataResponseDto<ActivityUpdateResponseDto> updateActicity(@PathVariable Long activityId, @RequestBody ActivityUpdateRequestDto requestDto){
        ActivityUpdateResponseDto responseDto = activityUpdateService.updateActivity(activityId,requestDto);
        return DataResponseDto.of(responseDto,"활동을 성공적으로 수정했습니다.");
    }

    @Operation(summary = "활동 추가", description = "유저가 갖고 있는 활동을 검색합니다. (활동의 description에 있는 내용은 아직 검색되지 않습니다.)")
    @PostMapping("/search")
    public DataResponseDto<ActivitySearchResponseDto> searchActivities(@RequestBody ActivitySearchRequestDto requestDto, @RequestParam Long userId) {
        ActivitySearchResponseDto responseDto = activitySearchService.searchActivities(requestDto.getSearch(), userId);
        return DataResponseDto.of(responseDto, "활동을 성공적으로 검색했습니다.");
    }
}