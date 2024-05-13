package com.hobak.happinessql.domain.user.dto;

import com.hobak.happinessql.domain.user.domain.Gender;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserDto {
    private Long userid;
    private String username;
    private String password;
    private String name;
    private Gender gender;
    private int age;

}
