package com.hobak.happinessql.domain.activity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityDto {
    private Long id;
    private String name;
    private String emoji;

    @Builder
    public ActivityDto(Long id, String name, String emoji) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
    }
}