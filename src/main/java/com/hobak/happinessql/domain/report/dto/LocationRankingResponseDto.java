package com.hobak.happinessql.domain.report.dto;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationRankingResponseDto {
    private int ranking;
    private String location;

    @Builder
    public LocationRankingResponseDto(int ranking, String location) {
        this.ranking = ranking;
        this.location = location;
    }
}
