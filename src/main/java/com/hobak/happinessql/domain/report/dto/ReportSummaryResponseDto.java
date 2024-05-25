package com.hobak.happinessql.domain.report.dto;

import com.hobak.happinessql.domain.report.domain.TimePeriod;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportSummaryResponseDto {
    private TimePeriod timePeriod;
    private String location;
    private String activity;

    @Builder
    public ReportSummaryResponseDto(TimePeriod timePeriod, String location, String activity) {
        this.timePeriod = timePeriod;
        this.location = location;
        this.activity = activity;
    }
}
