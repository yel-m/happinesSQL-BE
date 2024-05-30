package com.hobak.happinessql.domain.report.converter;
import com.hobak.happinessql.domain.report.dto.TrendPopularActivitiyResponseDto;
import com.hobak.happinessql.domain.report.dto.TrendRecommendActivityResponseDto;

public class TrendConverter {
    public static TrendPopularActivitiyResponseDto toTrendPopularActivitiesResponseDto(int ranking, String name, String emoji, Long times) {
        return TrendPopularActivitiyResponseDto
                .builder()
                .ranking(ranking)
                .times(times)
                .name(name)
                .emoji(emoji)
                .build();
    }

    public static TrendRecommendActivityResponseDto toTrendRecommendActivityResponseDto(String name, String emoji) {
        return TrendRecommendActivityResponseDto
                .builder()
                .name(name)
                .emoji(emoji)
                .build();
    }
}
