package com.hobak.happinessql.domain.user.application;

import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.exception.UserNotFoundException;
import com.hobak.happinessql.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFindService {

    private final UserRepository userRespository;

    public User findUserById(Long userId) {
        return userRespository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID" + userId));
    }
}
