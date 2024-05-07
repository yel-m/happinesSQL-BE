package com.hobak.happinessql.domain.activity.application;

import com.hobak.happinessql.domain.activity.converter.ActivityConverter;
import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.dto.ActivityUpdateRequestDto;
import com.hobak.happinessql.domain.activity.dto.ActivityUpdateResponseDto;
import com.hobak.happinessql.domain.activity.exception.ActivityNotFoundException;
import com.hobak.happinessql.domain.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityUpdateService {
    private final ActivityRepository activityRepository;

    public ActivityUpdateResponseDto updateActivity(Long activityId, ActivityUpdateRequestDto requestDto){
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(()-> new ActivityNotFoundException("Activity with id " + activityId));
        activity.updateName(requestDto.getName());
        return ActivityConverter.toActivityUpdateResponseDto(activity);
    }

}
