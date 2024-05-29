package com.hobak.happinessql.domain.report.converter;

import com.hobak.happinessql.domain.report.domain.HappinessLevel;
import com.hobak.happinessql.domain.report.dto.TrendHappinessResponseDto;
import com.hobak.happinessql.domain.report.dto.TrendPopularActivitiesResponseDto;

public class TrendConverter {
    public static TrendHappinessResponseDto toTrendHappinessResponseDto(double happiness, HappinessLevel level, String emoji) {
        return TrendHappinessResponseDto
                .builder()
                .happiness(happiness)
                .level(level)
                .emoji(emoji)
                .build();
    }
    public static TrendPopularActivitiesResponseDto toTrendPopularActivitiesResponseDto(int ranking, Long activityId, String name, String emoji, Long times) {
        return TrendPopularActivitiesResponseDto
                .builder()
                .activityId(activityId)
                .ranking(ranking)
                .times(times)
                .name(name)
                .emoji(emoji)
                .build();
    }
}
