package com.hobak.happinessql.domain.report.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AgeGroup {
    TEENS("10대", 10, 19),
    TWENTIES("20대", 20, 29),
    THIRTIES("30대", 30, 39),
    FORTIES("40대", 40, 49),
    FIFTIES("50대", 50, 59),
    SIXTIES("60대 이상", 60, 110);

    private final String viewName;
    private final int minAge;
    private final int maxAge;

    public static AgeGroup from(String value) {
        for (AgeGroup ageGroup : values()) {
            if (ageGroup.getViewName().equals(value)) {
                return ageGroup;
            }
        }
        throw new IllegalArgumentException("Invalid format: " + value + ".  Please use formats one of the following formats: '20대', '30대', ..., '60대 이상'");
    }

    @JsonValue
    public String getViewName() {
        return viewName;
    }

}
