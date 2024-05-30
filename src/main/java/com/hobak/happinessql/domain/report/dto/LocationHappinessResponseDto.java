package com.hobak.happinessql.domain.report.dto;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationHappinessResponseDto {
    private int ranking;
    private String location;

    @Builder
    public LocationHappinessResponseDto(int ranking, String location) {
        this.ranking = ranking;
        this.location = location;
    }
}
