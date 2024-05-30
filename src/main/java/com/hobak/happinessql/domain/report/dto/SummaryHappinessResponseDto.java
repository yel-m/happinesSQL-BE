package com.hobak.happinessql.domain.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hobak.happinessql.domain.report.domain.TimeOfDay;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SummaryHappinessResponseDto {

    @JsonProperty("time_of_day")
    private TimeOfDay timeOfDay;

    private String location;

    private String activity;

    @Builder
    public SummaryHappinessResponseDto(TimeOfDay timeOfDay, String location, String activity) {
        this.timeOfDay = timeOfDay;
        this.location = location;
        this.activity = activity;
    }
}
