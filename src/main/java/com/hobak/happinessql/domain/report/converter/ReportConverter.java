package com.hobak.happinessql.domain.report.converter;

import com.hobak.happinessql.domain.report.domain.HappinessLevel;
import com.hobak.happinessql.domain.report.domain.TimeOfDay;
import com.hobak.happinessql.domain.report.dto.*;

import java.util.ArrayList;

public class ReportConverter {
    public static SummaryResponseDto toSummaryResponseDto(TimeOfDay timeOfDay, String location, String activity) {
        return SummaryResponseDto.builder()
                .activity(activity)
                .location(location)
                .timeOfDay(timeOfDay)
                .build();
    }

    public static ActivityRankingResponseDto toActivityRankingResponseDto(int ranking, String activity, String emoji) {
        return ActivityRankingResponseDto.builder()
                .ranking(ranking)
                .activity(activity)
                .emoji(emoji)
                .build();
    }

    public static LocationRankingResponseDto toLocationRankingResponseDto(int ranking, String location) {
        return LocationRankingResponseDto.builder()
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

    public static TimeOfDayRankingResponseDto toTimeOfDayRankingResponseDto(int ranking, TimeOfDay timeOfDay) {
        return TimeOfDayRankingResponseDto.builder()
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
