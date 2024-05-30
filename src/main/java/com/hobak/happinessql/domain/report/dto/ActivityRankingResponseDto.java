package com.hobak.happinessql.domain.report.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityRankingResponseDto {

    private int ranking;
    private String activity;
    private String emoji;

    @Builder
    public ActivityRankingResponseDto(int ranking, String activity, String emoji) {
        this.ranking = ranking;
        this.activity = activity;
        this.emoji = emoji;
    }
}
