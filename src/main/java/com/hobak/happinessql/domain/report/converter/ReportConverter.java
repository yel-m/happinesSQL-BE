package com.hobak.happinessql.domain.report.converter;

import com.hobak.happinessql.domain.report.domain.TimeOfDay;
import com.hobak.happinessql.domain.report.dto.ActivityHappinessDto;
import com.hobak.happinessql.domain.report.dto.LocationHappinessDto;
import com.hobak.happinessql.domain.report.dto.ReportGraphResponseDto;
import com.hobak.happinessql.domain.report.dto.ReportSummaryResponseDto;

import java.util.ArrayList;

public class ReportConverter {
    public static ReportSummaryResponseDto toReportSummaryResponseDto(TimeOfDay timeOfDay, String location, String activity) {
        return ReportSummaryResponseDto.builder()
                .activity(activity)
                .location(location)
                .timeOfDay(timeOfDay)
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
}
