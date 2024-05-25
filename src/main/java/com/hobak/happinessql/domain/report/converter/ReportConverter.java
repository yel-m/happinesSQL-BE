package com.hobak.happinessql.domain.report.converter;

import com.hobak.happinessql.domain.report.domain.TimePeriod;
import com.hobak.happinessql.domain.report.dto.ActivityHappinessDto;
import com.hobak.happinessql.domain.report.dto.ReportSummaryResponseDto;

public class ReportConverter {
    public static ReportSummaryResponseDto toReportSummaryResponseDto(TimePeriod timePeriod, String location, String activity) {
        return ReportSummaryResponseDto.builder()
                .activity(activity)
                .location(location)
                .timePeriod(timePeriod)
                .build();
    }

    public static ActivityHappinessDto toActivityHappinessDto(int ranking, String activity, String emoji) {
        return ActivityHappinessDto.builder()
                .ranking(ranking)
                .activity(activity)
                .emoji(emoji)
                .build();
    }
}
