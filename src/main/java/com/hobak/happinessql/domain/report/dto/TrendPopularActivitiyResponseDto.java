package com.hobak.happinessql.domain.report.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrendPopularActivitiyResponseDto {
    private int ranking;
    private String name;
    private String emoji;
    private Long times;
    @Builder
    public TrendPopularActivitiyResponseDto(int ranking, String name, String emoji, Long times) {
        this.ranking = ranking;
        this.name = name;
        this.emoji = emoji;
        this.times = times;
    }

}
