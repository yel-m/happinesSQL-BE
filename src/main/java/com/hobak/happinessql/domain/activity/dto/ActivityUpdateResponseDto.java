package com.hobak.happinessql.domain.activity.dto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ActivityUpdateResponseDto {
    private Long categoryId;
    private String categoryName;
    private Long activityId;
    private String activityName;
    @Builder
    ActivityUpdateResponseDto(Long categoryId, String categoryName, Long activityId, String activityName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.activityId = activityId;
        this.activityName = activityName;
    }
}
