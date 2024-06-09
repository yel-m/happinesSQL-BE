package com.hobak.happinessql.domain.report.api;

import com.hobak.happinessql.domain.report.application.*;
import com.hobak.happinessql.domain.report.domain.AgeGroup;
import com.hobak.happinessql.domain.report.dto.*;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.domain.Gender;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Trend", description = "행복 트렌드 관련 REST API에 대한 명세를 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/trend")
public class TrendController {
    private final UserFindService userFindService;
    private final AverageHappinessService averageHappinessService;
    private final TrendPopularActivityService trendPopularActivityService;
    private final TrendRecommendService trendRecommendService;
    private final TrendSummaryService trendSummaryService;
    private final TrendLocationRankingService trendLocationRankingService;

    @Operation(summary = "대한민국 평균 행복지수", description = "전체 유저의 평균 행복지수와 그에 따른 수준을 판단합니다.")
    @GetMapping("/happiness")
    public DataResponseDto<AverageHappinessResponseDto> getAverageHappiness() {
        AverageHappinessResponseDto responseDto = averageHappinessService.getTrendHappiness();
        return DataResponseDto.of(responseDto, "대한민국 평균 행복지수를 성공적으로 조회했습니다.");
    }
    @Operation(summary = "오늘의 인기 활동 top3", description = "오늘 많이 기록된 활동의 이름과 기록 횟수를 조회합니다.")
    @GetMapping("/popular")
    public DataResponseDto<List<TrendPopularActivitiyResponseDto>> getPopularActivities() {
        List<TrendPopularActivitiyResponseDto> responseDto = trendPopularActivityService.getPopularActivities();
        return DataResponseDto.of(responseDto, "오늘의 인기 활동을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "추천 활동", description = "추천 활동을 조회합니다. 행복도가 5 이상인 활동 중에서 랜덤으로 하나를 추천받을 수 있습니다.")
    @GetMapping("/recommend")
    public DataResponseDto<List<TrendRecommendActivityResponseDto>> getRecommendedActivities(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<TrendRecommendActivityResponseDto> responseDto = trendRecommendService.getRecommendActivities(user);
        return DataResponseDto.of(responseDto, "추천 활동을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "연령대와 나이에 따른 행복 종합 리포트", description = "연령대와 나이를 쿼리 파라미터로 받아 그에 해당하는 행복 종합 리포트를 제공합니다. 만약 데이터가 없을 경우 data에 '아직은 데이터가 없습니다.' 문자열을 반환합니다.",
            parameters = {@Parameter(name="ageGroup", description = "지정하지 않으면 전체 연령으로 자동 설정됩니다."),
            @Parameter(name="gender", description = "gender를 지정하지 않으면 전체로 자동 설정됩니다.")
    })
    @GetMapping("/summary")
    public DataResponseDto<Object> getSummary(@RequestParam(required = false) AgeGroup ageGroup, @RequestParam(required = false) Gender gender) {
        SummaryResponseDto responseDto = trendSummaryService.getSummary(ageGroup, gender);
        if(responseDto == null) return DataResponseDto.of("아직은 데이터가 없어요.", "행복 트렌드의 행복 종합 리포트를 성공적으로 조회했습니다.");
        return DataResponseDto.of(responseDto, "행복 트렌드의 행복 종합 리포트를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "행복도가 높은 장소와 활동 Top3", description = "전체 유저가 행복했던 장소 Top 3의 이름, 위치, 그 장소에서 가장 행복도가 높았던 활동을 조회합니다.")
    @GetMapping("/top-locations")
    public DataResponseDto<List<LocationActivityRankingResponseDto>> getTop3HappiestLocations() {
        List<LocationActivityRankingResponseDto> responseDto = trendLocationRankingService.getTop3HappyLocationsWithActivities();
        return DataResponseDto.of(responseDto, "행복도가 높은 장소와 활동 Top3를 성공적으로 조회했습니다.");
    }
}
