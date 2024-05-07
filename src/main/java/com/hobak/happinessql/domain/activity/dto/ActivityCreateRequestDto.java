package com.hobak.happinessql.domain.activity.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityCreateRequestDto {
    private String category;
    private String activityName;
}
