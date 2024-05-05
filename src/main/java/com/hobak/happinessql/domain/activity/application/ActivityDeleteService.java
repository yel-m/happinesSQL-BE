package com.hobak.happinessql.domain.activity.application;


import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityDeleteService {
    private final ActivityRepository activityRepository;
    public void deleteActivity(Long activityId){
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(()->new IllegalArgumentException("해당 활동이 존재하지 않습니다. activityId: " + activityId));
        activityRepository.delete(activity);
    }
}
