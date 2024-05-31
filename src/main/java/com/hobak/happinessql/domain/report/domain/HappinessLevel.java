package com.hobak.happinessql.domain.report.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum HappinessLevel {
    VERY_LOW("매우 낮음","😱"),
    LOW("낮음","😐"),
    MEDIUM("보통","😐"),
    HIGH("높음", "🙂"),
    VERY_HIGH("매우 높음", "😄");
    private final String viewName;

    @Getter
    private final String emoji;
    public static HappinessLevel of(double happiness) {
        if (happiness >= 1 && happiness < 2) {
            return VERY_LOW;
        } else if (happiness >= 2 && happiness < 3) {
            return LOW;
        } else if (happiness >= 3 && happiness < 5) {
            return MEDIUM;
        } else if (happiness >= 5 && happiness < 6) {
            return HIGH;
        } else {
            return VERY_HIGH;
        }
    }
    @JsonValue
    public String getViewName() {
        return viewName;
    }

}
