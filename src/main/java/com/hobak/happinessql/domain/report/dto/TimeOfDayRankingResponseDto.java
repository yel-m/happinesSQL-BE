package com.hobak.happinessql.domain.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hobak.happinessql.domain.report.domain.TimeOfDay;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeOfDayRankingResponseDto {

    private int ranking;

    @JsonProperty("time_of_day")
    private TimeOfDay timeOfDay;

    @Builder
    public TimeOfDayRankingResponseDto(int ranking, TimeOfDay timeOfDay) {
        this.ranking = ranking;
        this.timeOfDay = timeOfDay;
    }
}
