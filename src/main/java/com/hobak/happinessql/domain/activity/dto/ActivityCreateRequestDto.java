package com.hobak.happinessql.domain.activity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityCreateRequestDto {
    private String category;
    private String activityName;
    @Builder
    ActivityCreateRequestDto(String category, String activityName){
        this.category = category;
        this.activityName = activityName;
    }
}
