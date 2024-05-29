package com.hobak.happinessql.domain.report.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrendPopularActivitiesResponseDto {
    private int ranking;
    private Long activityId;
    private String name;
    private String emoji;
    private Long times;
    @Builder
    public TrendPopularActivitiesResponseDto(int ranking, Long activityId, String name, String emoji, Long times) {
        this.ranking = ranking;
        this.activityId = activityId;
        this.name = name;
        this.emoji = emoji;
        this.times = times;
    }

}
