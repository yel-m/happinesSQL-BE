package com.hobak.happinessql.domain.record.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordCreateRequestDto {

    @NotNull
    @JsonProperty("activity_id")
    private Long activityId;

    @NotNull
    private int happiness;

    private String memo;

    @NotNull
    @JsonProperty("full_name")
    private String fullName;

    @NotNull
    private String country;

    @NotNull
    private String city;

    @NotNull
    private String district;

    @NotNull
    @JsonProperty("x_pos")
    private Double xPos;

    @NotNull
    @JsonProperty("y_pos")
    private Double yPos;
}
