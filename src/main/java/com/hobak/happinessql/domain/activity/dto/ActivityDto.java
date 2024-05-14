package com.hobak.happinessql.domain.activity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityDto {
    private Long id;
    private String name;
    private List<String> description;
    private String emoji;

    @Builder
    public ActivityDto(Long id, String name, List<String> description, String emoji) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.emoji = emoji;
    }
}