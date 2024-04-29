package com.hobak.happinessql.domain.activity.repository;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.domain.Category;
import com.hobak.happinessql.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByCategory(Category category);
    @Query("SELECT MAX(a.activityId) FROM Activity a")
    Long findMaxActivityId();
}
