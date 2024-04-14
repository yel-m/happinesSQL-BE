package com.hobak.happinessql.domain.user.application;

import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.exception.UserNotFoundException;
import com.hobak.happinessql.domain.user.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFindService {

    private final UserRespository userRespository;

    public User findUserById(Long userId) {
        return userRespository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID" + userId));
    }
}
