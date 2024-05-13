package com.hobak.happinessql.domain.record.application;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.exception.ActivityNotFoundException;
import com.hobak.happinessql.domain.activity.repository.ActivityRepository;
import com.hobak.happinessql.domain.record.converter.RecordConverter;
import com.hobak.happinessql.domain.record.domain.Location;
import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.domain.RecordImg;
import com.hobak.happinessql.domain.record.dto.RecordCreateRequestDto;
import com.hobak.happinessql.domain.record.repository.LocationRepository;
import com.hobak.happinessql.domain.record.repository.RecordImgRepository;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.global.infra.aws.AwsS3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RecordCreateService {

    private final RecordRepository recordRepository;
    private final RecordImgRepository recordImgRepository;
    private final ActivityRepository activityRepository;
    private final LocationRepository locationRepository;

    private final AwsS3Service awsS3Service;
    private final UserFindService userFindService;


    @Transactional
    public Long createRecord(Long userId, RecordCreateRequestDto requestDto, MultipartFile img) {

        User user = userFindService.findUserById(userId);

        Activity activity = activityRepository.findById(requestDto.getActivityId())
                .orElseThrow(() -> new ActivityNotFoundException("Activity with ID " + requestDto.getActivityId()));

        Record record = RecordConverter.toRecord(requestDto, user, activity);
        Record newRecord = recordRepository.save(record);

        Location location = RecordConverter.toLocation(requestDto, newRecord);
        locationRepository.save(location);

        if (img != null && !img.isEmpty()) {
            String imgUrl = awsS3Service.uploadFile(img);
            RecordImg recordImg = RecordImg.builder()
                    .url(imgUrl)
                    .record(newRecord)
                    .build();
            recordImgRepository.save(recordImg);
        }

        return newRecord.getRecordId();
    }


}
