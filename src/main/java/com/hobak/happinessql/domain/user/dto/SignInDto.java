package com.hobak.happinessql.domain.user.dto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SignInDto {
    private String username;
    private String password;
}
