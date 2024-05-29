package com.hobak.happinessql.domain.report.api;

import com.hobak.happinessql.domain.report.application.TrendHappinessService;
import com.hobak.happinessql.domain.report.application.TrendPopularActivitiesService;
import com.hobak.happinessql.domain.report.dto.TrendHappinessResponseDto;
import com.hobak.happinessql.domain.report.dto.TrendPopularActivitiesResponseDto;
import com.hobak.happinessql.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Trend", description = "행복 트렌드 관련 REST API에 대한 명세를 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/trend")
public class TrendController {
    private final TrendHappinessService trendHappinessService;
    private final TrendPopularActivitiesService trendPopularActivitiesService;
    @Operation(summary = "대한민국 평균 행복지수", description = "전체 유저의 평균 행복지수와 그에 따른 수준을 판단합니다.")
    @GetMapping("/happiness")
    public DataResponseDto<TrendHappinessResponseDto> getHappiness() {
        TrendHappinessResponseDto responseDto = trendHappinessService.getTrendHappiness();
        return DataResponseDto.of(responseDto, "대한민국 평균 행복지수를 성공적으로 조회했습니다.");
    }
    @Operation(summary = "오늘의 인기 활동 top3", description = "오늘 많이 기록된 활동의 이름과 기록 횟수를 조회합니다.")
    @GetMapping("/popular")
    public DataResponseDto<List<TrendPopularActivitiesResponseDto>> getPopular() {
        List<TrendPopularActivitiesResponseDto> responseDto = trendPopularActivitiesService.getPopularActivities();
        return DataResponseDto.of(responseDto, "오늘의 인기 활동을 성공적으로 조회했습니다.");
    }
}
