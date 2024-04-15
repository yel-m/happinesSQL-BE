package com.hobak.happinessql.domain.user.repository;

import com.hobak.happinessql.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
