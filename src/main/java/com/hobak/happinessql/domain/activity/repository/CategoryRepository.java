package com.hobak.happinessql.domain.activity.repository;

import com.hobak.happinessql.domain.activity.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findByName(String name);
    List<Category> findByUserIdIsNull();

    Category findByUserId(Long userId);
}