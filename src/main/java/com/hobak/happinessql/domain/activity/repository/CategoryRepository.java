package com.hobak.happinessql.domain.activity.repository;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Page<Category> findByCategoryIdGreaterThanOrderByCategoryIdAsc(Long lastCategoryId, Pageable pageable);
}