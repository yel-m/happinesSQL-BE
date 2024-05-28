package com.hobak.happinessql.domain.report.converter;

import com.hobak.happinessql.domain.report.domain.HappinessLevel;
import com.hobak.happinessql.domain.report.dto.TrendHappinessResponseDto;

public class TrendConverter {
    public static TrendHappinessResponseDto toTrendHappinessResponseDto(double happiness, HappinessLevel level, String emoji) {
        return TrendHappinessResponseDto
                .builder()
                .happiness(happiness)
                .level(level)
                .emoji(emoji)
                .build();
    }
}
