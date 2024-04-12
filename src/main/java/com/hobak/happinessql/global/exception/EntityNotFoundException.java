package com.hobak.happinessql.global.exception;

import com.hobak.happinessql.global.response.Code;

public class EntityNotFoundException extends GeneralException{

    public EntityNotFoundException(String message) {
        super(Code.ENTITY_NOT_FOUND, message);
    }
}
