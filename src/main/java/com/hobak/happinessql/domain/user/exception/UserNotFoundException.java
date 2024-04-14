package com.hobak.happinessql.domain.user.exception;

import com.hobak.happinessql.global.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String target) {
        super(target + "is not found");
    }
}