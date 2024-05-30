package com.hobak.happinessql.domain.report.dto;

import com.hobak.happinessql.domain.report.domain.HappinessLevel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AverageHappinessResponseDto {
    private double happiness;
    private HappinessLevel level;
    private String emoji;
    @Builder
    public AverageHappinessResponseDto(double happiness, HappinessLevel level, String emoji){
        this.happiness = happiness;
        this.level = level;
        this.emoji = emoji;
    }
}
