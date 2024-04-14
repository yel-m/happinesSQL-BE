package com.hobak.happinessql.domain.activity.exception;

import com.hobak.happinessql.global.exception.EntityNotFoundException;

public class ActivityNotFoundException extends EntityNotFoundException {
    public ActivityNotFoundException(String target) {
        super(target + " is not found");
    }
}