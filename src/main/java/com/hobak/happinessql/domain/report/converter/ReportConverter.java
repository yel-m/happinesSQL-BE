package com.hobak.happinessql.domain.report.converter;

import com.hobak.happinessql.domain.report.domain.HappinessLevel;
import com.hobak.happinessql.domain.report.domain.TimePeriod;
import com.hobak.happinessql.domain.report.dto.*;

import java.util.ArrayList;

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

    public static LocationHappinessDto toLocationHappinessDto(int ranking, String location) {
        return LocationHappinessDto.builder()
                .ranking(ranking)
                .location(location)
                .build();
    }
    public static ReportGraphResponseDto toReportGraphResponseDto(ArrayList<String> labels, ArrayList<Double> happiness){
        return ReportGraphResponseDto.builder()
                .labels(labels)
                .happiness(happiness)
                .build();
    }
    public static AverageHappinessResponseDto toAverageHappinessResponseDto(double happiness, HappinessLevel level, String emoji) {
        return AverageHappinessResponseDto
                .builder()
                .happiness(happiness)
                .level(level)
                .emoji(emoji)
                .build();
    }
}
