package com.hobak.happinessql.domain.report.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hobak.happinessql.domain.user.domain.Gender;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimePeriod {
    MORNING("아침"),
    AFTERNOON("점심"),
    EVENING("저녁"),
    NIGHT("밤");

    private final String viewName;

    public static TimePeriod of(int hour) {
        if (hour >= 6 && hour < 12) {
            return MORNING;
        } else if (hour >= 12 && hour < 18) {
            return AFTERNOON;
        } else if (hour >= 18 && hour < 24) {
            return EVENING;
        } else {
            return NIGHT;
        }
    }

    @JsonCreator
    public static TimePeriod from(String value) {
        for (TimePeriod status : TimePeriod.values()) {
            if (status.getViewName().equals(value)) {
                return status;
            }
        }
        return null;
    }

    @JsonValue
    public String getViewName() {
        return viewName;
    }
}
