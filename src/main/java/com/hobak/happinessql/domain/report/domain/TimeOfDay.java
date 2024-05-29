package com.hobak.happinessql.domain.report.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimeOfDay {
    DAWN("새벽"),
    MORNING("아침"),
    AFTERNOON("낮"),
    EVENING("저녁"),
    NIGHT("밤");

    private final String viewName;

    public static TimeOfDay of(int hour) {
        if (hour >= 0 && hour < 5) {
            return DAWN;
        } else if (hour >= 5 && hour < 9) {
            return MORNING;
        } else if (hour >= 9 && hour < 17) {
            return AFTERNOON;
        } else if (hour >= 17 && hour < 21) {
            return EVENING;
        } else {
            return NIGHT;
        }
    }

    @JsonCreator
    public static TimeOfDay from(String value) {
        for (TimeOfDay status : TimeOfDay.values()) {
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
