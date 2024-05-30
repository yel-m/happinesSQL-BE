package com.hobak.happinessql.domain.report.api;


import com.hobak.happinessql.domain.report.application.*;
import com.hobak.happinessql.domain.report.dto.*;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Report", description = "행복 분석 리포트 관련 REST API에 대한 명세를 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/report")
public class ReportController {

    private final UserFindService userFindService;
    private final ReportSummaryService reportSummaryService;
    private final ReportActivityRankingService reportActivityRankingService;
    private final ReportLocationRankingService reportLocationRankingService;
    private final ReportTimeOfPeriodRankingService reportTimeOfPeriodRankingService;
    private final ReportGraphService reportGraphService;
    private final AverageHappinessService averageHappinessService;
    @Operation(summary = "[전체] 행복 종합 리포트", description = "전체 기간에서 언제, 어디에서, 무엇을 할 때 행복했는지에 대한 종합적인 리포트를 제공합니다.")
    @GetMapping("/all/summary")
    public DataResponseDto<ReportSummaryResponseDto> getAllSummary(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        ReportSummaryResponseDto responseDto = reportSummaryService.getAllSummary(user);
        return DataResponseDto.of(responseDto, "행복 종합 리포트(전체)를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[연간] 행복 종합 리포트", description = "이번 해 언제, 어디에서, 무엇을 할 때 행복했는지에 대한 종합적인 리포트를 제공합니다.")
    @GetMapping("/year/summary")
    public DataResponseDto<ReportSummaryResponseDto> getAnnualSummary(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        ReportSummaryResponseDto responseDto = reportSummaryService.getAnnualSummary(user);
        return DataResponseDto.of(responseDto, "행복 종합 리포트(연간)를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[월간] 행복 종합 리포트", description = "이번 달 언제, 어디에서, 무엇을 할 때 행복했는지에 대한 종합적인 리포트를 제공합니다.")
    @GetMapping("/month/summary")
    public DataResponseDto<ReportSummaryResponseDto> getMonthlySummary(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        ReportSummaryResponseDto responseDto = reportSummaryService.getMonthlySummary(user);
        return DataResponseDto.of(responseDto, "행복 종합 리포트(월간)를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[전체] 행복도가 높은 Top 3 활동", description = "전체 기록에서 가장 행복도가 높은 Top 3 활동을 제공합니다.")
    @GetMapping("/all/top-activities")
    public DataResponseDto<List<ActivityHappinessDto>> getTop3AllHappiestActivities(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<ActivityHappinessDto> responseDto = reportActivityRankingService.getTop3AllHappiestActivities(user);
        return DataResponseDto.of(responseDto, "행복도가 높은 Top 3 활동(전체)을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[연간] 행복도가 높은 Top 3 활동", description = "이번 해 가장 행복도가 높은 Top 3 활동을 제공합니다.")
    @GetMapping("/year/top-activities")
    public DataResponseDto<List<ActivityHappinessDto>> getTop3AnnualHappiestActivities(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<ActivityHappinessDto> responseDto = reportActivityRankingService.getTop3AnnualHappiestActivities(user);
        return DataResponseDto.of(responseDto, "행복도가 높은 Top 3 활동(연간)을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[월간] 행복도가 높은 Top 3 활동", description = "이번 해 가장 행복도가 높은 Top 3 활동을 제공합니다.")
    @GetMapping("/month/top-activities")
    public DataResponseDto<List<ActivityHappinessDto>> getTop3MonthlyHappiestActivities(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<ActivityHappinessDto> responseDto = reportActivityRankingService.getTop3MonthlyHappiestActivities(user);
        return DataResponseDto.of(responseDto, "행복도가 높은 Top 3 활동(월간)을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[전체] 행복도가 높은 Top 3 위치", description = "전체 기록에서 가장 행복도가 높은 Top 3 위치을 제공합니다.")
    @GetMapping("/all/top-locations")
    public DataResponseDto<List<LocationHappinessDto>> getTop3AllHappiestLocations(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<LocationHappinessDto> responseDto = reportLocationRankingService.getTop3AllHappiestLocations(user);
        return DataResponseDto.of(responseDto, "행복도가 높은 Top 3 위치(전체)를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[연간] 행복도가 높은 Top 3 위치", description = "이번 해 가장 행복도가 높은 Top 3 위치를 제공합니다.")
    @GetMapping("/year/top-locations")
    public DataResponseDto<List<LocationHappinessDto>> getTop3AnnualHappiestLocations(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<LocationHappinessDto> responseDto = reportLocationRankingService.getTop3AnnualHappiestLocations(user);
        return DataResponseDto.of(responseDto, "행복도가 높은 Top 3 위치(연간)를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[월간] 행복도가 높은 Top 3 위치", description = "이번 해 가장 행복도가 높은 Top 3 위치를 제공합니다.")
    @GetMapping("/month/top-locations")
    public DataResponseDto<List<LocationHappinessDto>> getTop3MonthlyHappiestLocations(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<LocationHappinessDto> responseDto = reportLocationRankingService.getTop3MonthlyHappiestLocations(user);
        return DataResponseDto.of(responseDto, "행복도가 높은 Top 3 위치(월간)를 성공적으로 조회했습니다.");
    }
    @Operation(summary = "[연간] 행복 그래프", description = "연간 행복지수 그래프를 제공합니다.")
    @GetMapping("/year/graph")
    public DataResponseDto<ReportGraphResponseDto> getAnnualGraph(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        ReportGraphResponseDto responseDto = reportGraphService.getAnnualGraph(user);
        return DataResponseDto.of(responseDto, "연간 행복지수 그래프를 성공적으로 조회했습니다.");
    }
    @Operation(summary = "[월간] 행복 그래프", description = "월간 행복지수 그래프를 제공합니다.")
    @GetMapping("/month/graph")
    public DataResponseDto<ReportGraphResponseDto> getMonthlyGraph(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        ReportGraphResponseDto responseDto = reportGraphService.getMonthlyGraph(user);
        return DataResponseDto.of(responseDto, "월간 행복지수 그래프를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[전체] 모든 활동의 행복도 순위", description = "전체 기록에서 모든 활동의 행복도 순위를 제공합니다.")
    @GetMapping("/all/ranking/activities")
    public DataResponseDto<List<ActivityHappinessDto>> getAllActivityRankings(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<ActivityHappinessDto> responseDto = reportActivityRankingService.getAllActivityRankings(user);
        return DataResponseDto.of(responseDto, "전체 활동 행복도 순위를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[연간] 모든 활동의 행복도 순위", description = "이번 해 모든 활동의 행복도 순위를 제공합니다.")
    @GetMapping("/year/ranking/activities")
    public DataResponseDto<List<ActivityHappinessDto>> getYearlyActivityRankings(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<ActivityHappinessDto> responseDto = reportActivityRankingService.getYearlyActivityRankings(user);
        return DataResponseDto.of(responseDto, "연간 활동 행복도 순위를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[월간] 모든 활동의 행복도 순위", description = "이번 달 모든 활동의 행복도 순위를 제공합니다.")
    @GetMapping("/month/ranking/activities")
    public DataResponseDto<List<ActivityHappinessDto>> getMonthlyActivityRankings(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<ActivityHappinessDto> responseDto = reportActivityRankingService.getMonthlyActivityRankings(user);
        return DataResponseDto.of(responseDto, "월간 활동 행복도 순위를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[전체] 모든 위치의 행복도 순위", description = "전체 기록에서 모든 위치의 행복도 순위를 제공합니다.")
    @GetMapping("/all/ranking/locations")
    public DataResponseDto<List<LocationHappinessDto>> getAllLocationRankings(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<LocationHappinessDto> responseDto = reportLocationRankingService.getAllLocationRankings(user);
        return DataResponseDto.of(responseDto, "전체 위치 행복도 순위를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[연간] 모든 위치의 행복도 순위", description = "이번 해 모든 위치의 행복도 순위를 제공합니다.")
    @GetMapping("/year/ranking/locations")
    public DataResponseDto<List<LocationHappinessDto>> getYearlyLocationRankings(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<LocationHappinessDto> responseDto = reportLocationRankingService.getYearlyLocationRankings(user);
        return DataResponseDto.of(responseDto, "연간 위치 행복도 순위를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[월간] 모든 위치의 행복도 순위", description = "이번 달 모든 위치의 행복도 순위를 제공합니다.")
    @GetMapping("/month/ranking/locations")
    public DataResponseDto<List<LocationHappinessDto>> getMonthlyLocationRankings(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<LocationHappinessDto> responseDto = reportLocationRankingService.getMonthlyLocationRankings(user);
        return DataResponseDto.of(responseDto, "월간 위치 행복도 순위를 성공적으로 조회했습니다.");
    }
  
    @Operation(summary = "[전체] 시간대 행복도 순위", description = "전체 기록에서 시간대 행복도 순위를 제공합니다.")
    @GetMapping("/all/ranking/time")
    public DataResponseDto<List<TimeOfDayHappinessDto>> getAllTimeOfDayRankings(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<TimeOfDayHappinessDto> responseDto = reportTimeOfPeriodRankingService.getAllTimeOfDayRankings(user);
        return DataResponseDto.of(responseDto, "전체 시간대 행복도 순위를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[연간] 시간대 행복도 순위", description = "이번 해 시간대 행복도 순위를 제공합니다.")
    @GetMapping("/year/ranking/time")
    public DataResponseDto<List<TimeOfDayHappinessDto>> getYearlyTimeOfDayRankings(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<TimeOfDayHappinessDto> responseDto = reportTimeOfPeriodRankingService.getYearlyTimeOfDayRankings(user);
        return DataResponseDto.of(responseDto, "연간 시간대 행복도 순위를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[월간] 시간대 행복도 순위", description = "이번 달 시간대 행복도 순위를 제공합니다.")
    @GetMapping("/month/ranking/time")
    public DataResponseDto<List<TimeOfDayHappinessDto>> getMonthlyTimeOfDayRankings(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<TimeOfDayHappinessDto> responseDto = reportTimeOfPeriodRankingService.getMonthlyTimeOfDayRankings(user);
        return DataResponseDto.of(responseDto, "월간 시간대 행복도 순위를 성공적으로 조회했습니다.");
    }
    @Operation(summary = "[전체] 평균 행복지수", description = "유저 개인의 전체기간 평균 행복지수와 그에 따른 수준을 판단합니다.")
    @GetMapping("/all/happiness/")
    public DataResponseDto<AverageHappinessResponseDto> getAllHappiness(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        AverageHappinessResponseDto responseDto = averageHappinessService.getAllHappiness(user);
        return DataResponseDto.of(responseDto, "유저 개인의 전체기간 평균 행복지수를 성공적으로 조회했습니다.");
    }
    @Operation(summary = "[연간] 평균 행복지수", description = "유저 개인의 연간 평균 행복지수와 그에 따른 수준을 판단합니다.")
    @GetMapping("/year/happiness")
    public DataResponseDto<AverageHappinessResponseDto> getAnnualHappiness(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        AverageHappinessResponseDto responseDto = averageHappinessService.getAnnualHappiness(user);
        return DataResponseDto.of(responseDto, "유저 개인의 연간 평균 행복지수를 성공적으로 조회했습니다.");
    }
    @Operation(summary = "[월간] 평균 행복지수", description = "유저 개인의 월간 평균 행복지수와 그에 따른 수준을 판단합니다.")
    @GetMapping("/month/happiness")
    public DataResponseDto<AverageHappinessResponseDto> getMonthlyHappiness(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        AverageHappinessResponseDto responseDto = averageHappinessService.getMonthlyHappiness(user);
        return DataResponseDto.of(responseDto, "유저 개인의 월간 평균 행복지수를 성공적으로 조회했습니다.");
    }

}
