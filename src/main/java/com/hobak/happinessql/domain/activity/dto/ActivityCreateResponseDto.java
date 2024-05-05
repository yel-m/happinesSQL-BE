package com.hobak.happinessql.domain.activity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityCreateResponseDto {
    private Long activityId;
    @Builder
    ActivityCreateResponseDto(Long activityId){
        this.activityId = activityId;
    }
}
