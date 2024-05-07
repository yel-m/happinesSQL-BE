package com.hobak.happinessql.domain.activity.repository;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByCategory(Category category);
    @Query(value = "SELECT MAX(a.activityId) + 1 FROM Activity a")
    Long findNextActivityId();

    List<Activity> findByCategoryNameContaining(String keyword);
    List<Activity> findByNameContaining(String keyword);
}
