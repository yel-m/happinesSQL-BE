package com.hobak.happinessql.domain.record.application;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.activity.exception.ActivityNotFoundException;
import com.hobak.happinessql.domain.activity.repository.ActivityRepository;
import com.hobak.happinessql.domain.record.converter.RecordConverter;
import com.hobak.happinessql.domain.record.domain.Location;
import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.domain.RecordImg;
import com.hobak.happinessql.domain.record.dto.RecordRequestDto;
import com.hobak.happinessql.domain.record.repository.LocationRepository;
import com.hobak.happinessql.domain.record.repository.RecordImgRepository;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.global.infra.s3.AwsS3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RecordCreationService {

    private final RecordRepository recordRepository;
    private final RecordImgRepository recordImgRepository;
    private final ActivityRepository activityRepository;
    private final LocationRepository locationRepository;

    private final AwsS3Service awsS3Service;
    private final UserFindService userFindService;


    @Transactional
    public Long createRecord(Long userId, RecordCreationRequestDto recordRequestDto, MultipartFile img) {

        // 사용자 찾기
        User user = userFindService.findUserById(userId);

        // 활동 찾기
        Activity activity = activityRepository.findById(recordRequestDto.getActivityId())
                .orElseThrow(() -> new ActivityNotFoundException("Activity with ID " + recordRequestDto.getActivityId()));

        // 기록 생성
        Record record = RecordConverter.toRecord(recordRequestDto, user, activity);
        Record newRecord = recordRepository.save(record);

        // 위치 저장
        Location location = RecordConverter.toLocation(recordRequestDto, newRecord);
        locationRepository.save(location);

        // 이미지 업로드
        if (img != null && !img.isEmpty()) {
            String imgUrl = awsS3Service.uploadFile(img);
            RecordImg recordImg = RecordImg.builder()
                    .url(imgUrl)
                    .record(newRecord)
                    .build();
            recordImgRepository.save(recordImg);
        }
        // 생성된 Record의 ID 반환
        return newRecord.getRecordId();
    }




}
