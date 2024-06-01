package com.hobak.happinessql.domain.report.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationActivityRankingResponseDto {
    private int ranking;
    private String location;
    private Double latitude;
    private Double longitude;
    private String happiestActivity;

    @Builder
    public LocationActivityRankingResponseDto(int ranking, String location, Double latitude, Double longitude, String happiestActivity) {
        this.ranking = ranking;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.happiestActivity = happiestActivity;
    }
}
