package com.hobak.happinessql.domain.report.converter;
import com.hobak.happinessql.domain.report.dto.TrendPopularActivitiesResponseDto;

public class TrendConverter {
    public static TrendPopularActivitiesResponseDto toTrendPopularActivitiesResponseDto(int ranking, String name, String emoji, Long times) {
        return TrendPopularActivitiesResponseDto
                .builder()
                .ranking(ranking)
                .times(times)
                .name(name)
                .emoji(emoji)
                .build();
    }
}
