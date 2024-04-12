package com.hobak.happinessql.global.response;

import com.hobak.happinessql.global.exception.GeneralException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum Code {

    // 충돌 방지를 위한 Code format
    // X1XXX: 이상은
    // X2XXX: 정예림
    // ex) 이상은이 닉네임 중복 에러코드를 만든다면
    // USER_NICKNAME_DUPLICATED(11010, HttpStatus.BAD_REQUEST, "User nickname duplicated"),

    // Common
    OK(0, HttpStatus.OK, "Ok"),

    BAD_REQUEST(10000, HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(10001, HttpStatus.BAD_REQUEST, "Validation error"),
    ENTITY_NOT_FOUND(404, HttpStatus.NOT_FOUND, " Entity Not Found"),
    METHOD_NOT_ALLOWED(10004, HttpStatus.METHOD_NOT_ALLOWED, "대상 리소스가 이 메서드를 지원하지 않습니다."),

    INTERNAL_ERROR(10005, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR(10006, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),

    // User
    UNAUTHORIZED(30000, HttpStatus.UNAUTHORIZED, "User unauthorized"),


    // Record
    RECORD_NOT_FOUND(40000, HttpStatus.NOT_FOUND, "존재하지 않는 행복 기록입니다.");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid" 
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public static Code valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) {
                        return Code.BAD_REQUEST;
                    } else if (httpStatus.is5xxServerError()) {
                        return Code.INTERNAL_ERROR;
                    } else {
                        return Code.OK;
                    }
                });
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}