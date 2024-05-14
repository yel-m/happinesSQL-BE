package com.hobak.happinessql.domain.user.application;

import com.hobak.happinessql.domain.user.converter.UserConverter;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(()->new UsernameNotFoundException("해당하는 회원이 없습니다."));
    }
    private UserDetails createUserDetails(User user) {
        return UserConverter.toUserDetails(user);
    }
}
