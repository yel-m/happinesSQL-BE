package com.hobak.happinessql.domain.record.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordRequestDto {

    @NotNull
    private Long activity_id;

    @NotNull
    private int happiness;

    private String memo;

    @NotNull
    private String full_name;

    @NotNull
    private String country;

    @NotNull
    private String city;

    @NotNull
    private String district;

    @NotNull
    private Double x_pos;

    @NotNull
    private Double y_pos;
}
