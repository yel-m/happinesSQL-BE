package com.hobak.happinessql.domain.user.converter;

import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.dto.SignUpDto;
import com.hobak.happinessql.domain.user.dto.UserDto;
import com.hobak.happinessql.domain.user.dto.UserInfoResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public class UserConverter {
    public static UserInfoResponseDto toUserInfoResponseDto(User user) {
        return UserInfoResponseDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .build();
    }
    public static UserDetails toUserDetails(User user){
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }

    public static User toUserEntity(SignUpDto signUpDto, String encodedPassword, List<String> roles) {

        return User.builder()
                .username(signUpDto.getUsername())
                .password(encodedPassword)
                .name(signUpDto.getName())
                .gender(signUpDto.getGender())
                .age(signUpDto.getAge())
                .roles(roles)
                .build();
    }
    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .userid(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .gender(user.getGender())
                .age(user.getAge()).build();
    }

}
