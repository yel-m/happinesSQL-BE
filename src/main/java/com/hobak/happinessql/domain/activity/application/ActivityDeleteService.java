package com.hobak.happinessql.domain.activity.application;


import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.exception.ActivityNotFoundException;
import com.hobak.happinessql.domain.activity.repository.ActivityRepository;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityDeleteService {
    private final ActivityRepository activityRepository;
    public Long deleteActivity(Long activityId, User user){
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(()->new ActivityNotFoundException("Activity with id " + activityId));
        if(activity.getCategory().getUserId().equals(user.getUserId())) {
            activityRepository.delete(activity);
            return activity.getActivityId();
        }
        return null;
    }
}
