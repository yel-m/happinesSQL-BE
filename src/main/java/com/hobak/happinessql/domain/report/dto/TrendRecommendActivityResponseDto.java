package com.hobak.happinessql.domain.report.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrendRecommendActivityResponseDto {
    private String name;
    private String emoji;

    @Builder
    public TrendRecommendActivityResponseDto(String name, String emoji) {
        this.name = name;
        this.emoji = emoji;
    }
}
