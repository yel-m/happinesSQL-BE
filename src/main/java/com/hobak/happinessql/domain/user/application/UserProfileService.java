package com.hobak.happinessql.domain.user.application;

import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.dto.UserProfileUpdateRequestDto;
import com.hobak.happinessql.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    public User updateUserProfile(User user, UserProfileUpdateRequestDto requestDto) {
        user.updateUserProfile(requestDto);
        userRepository.save(user);
        return user;
    }
}
