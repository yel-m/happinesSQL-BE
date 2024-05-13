package com.hobak.happinessql.domain.user.dto;
import com.hobak.happinessql.domain.user.domain.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpDto {
    private String username;
    private String password;
    private String name;
    private Gender gender;
    private int age;
    private List<String> roles = new ArrayList<>();
}
