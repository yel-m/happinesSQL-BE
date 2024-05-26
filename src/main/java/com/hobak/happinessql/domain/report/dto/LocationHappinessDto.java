package com.hobak.happinessql.domain.report.dto;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationHappinessDto {
    private int ranking;
    private String location;

    @Builder
    public LocationHappinessDto(int ranking, String location) {
        this.ranking = ranking;
        this.location = location;
    }
}
