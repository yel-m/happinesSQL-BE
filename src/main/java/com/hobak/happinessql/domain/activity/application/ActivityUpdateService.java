package com.hobak.happinessql.domain.activity.application;

import com.hobak.happinessql.domain.activity.converter.ActivityConverter;
import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.dto.ActivityUpdateRequestDto;
import com.hobak.happinessql.domain.activity.dto.ActivityUpdateResponseDto;
import com.hobak.happinessql.domain.activity.exception.ActivityNotFoundException;
import com.hobak.happinessql.domain.activity.repository.ActivityRepository;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityUpdateService {
    private final ActivityRepository activityRepository;

    public ActivityUpdateResponseDto updateActivity(Long activityId, ActivityUpdateRequestDto requestDto, User user){
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(()-> new ActivityNotFoundException("Activity with id " + activityId));
        if(activity.getCategory().getUserId().equals(user.getUserId())) {
            activity.updateName(requestDto.getName());
            return ActivityConverter.toActivityUpdateResponseDto(activity);
        }
        return null;
    }

}
