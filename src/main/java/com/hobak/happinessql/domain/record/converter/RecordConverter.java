package com.hobak.happinessql.domain.record.converter;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.record.domain.Location;
import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.dto.RecordRequestDto;
import com.hobak.happinessql.domain.record.dto.RecordCreationRequestDto;
import com.hobak.happinessql.domain.user.domain.User;

public class RecordConverter {

    public static Record toRecord(RecordCreationRequestDto recordRequestDto, User user, Activity activity) {
        return Record.builder()
                .happiness(recordRequestDto.getHappiness())
                .memo(recordRequestDto.getMemo())
                .user(user)
                .activity(activity)
                .build();
    }

    public static Location toLocation(RecordCreationRequestDto recordRequestDto, Record record) {
        return Location.builder()
                .fullName(recordRequestDto.getFullName())
                .city(recordRequestDto.getCity())
                .country(recordRequestDto.getCountry())
                .district(recordRequestDto.getDistrict())
                .xPos(recordRequestDto.getXPos())
                .yPos(recordRequestDto.getYPos())
                .record(record)
                .build();
    }


}
