package com.hobak.happinessql.domain.report.converter;

import com.hobak.happinessql.domain.report.domain.HappinessLevel;
import com.hobak.happinessql.domain.report.domain.TimeOfDay;
import com.hobak.happinessql.domain.report.dto.*;

import java.util.ArrayList;

public class ReportConverter {
    public static ReportSummaryResponseDto toReportSummaryResponseDto(TimeOfDay timeOfDay, String location, String activity) {
        return ReportSummaryResponseDto.builder()
                .activity(activity)
                .location(location)
                .timeOfDay(timeOfDay)
                .build();
    }

    public static ActivityHappinessResponseDto toActivityHappinessResponseDto(int ranking, String activity, String emoji) {
        return ActivityHappinessResponseDto.builder()
                .ranking(ranking)
                .activity(activity)
                .emoji(emoji)
                .build();
    }

    public static LocationHappinessResponseDto toLocationHappinessResponseDto(int ranking, String location) {
        return LocationHappinessResponseDto.builder()
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
    public static TimeOfDayHappinessResponseDto toTimeOfDayHappinessResponseDto(int ranking, TimeOfDay timeOfDay) {
        return TimeOfDayHappinessResponseDto.builder()
                .ranking(ranking)
                .timeOfDay(timeOfDay)
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
