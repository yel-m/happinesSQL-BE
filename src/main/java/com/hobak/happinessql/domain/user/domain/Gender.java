package com.hobak.happinessql.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum Gender {
    MALE("남성"),
    FEMALE("여성"),
    NO_CHOICE("선택 안함");

    private final String viewName;

    @JsonCreator
    public static Gender from(String value) {
        for (Gender status : Gender.values()) {
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
