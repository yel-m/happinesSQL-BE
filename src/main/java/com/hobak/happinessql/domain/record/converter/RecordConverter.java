package com.hobak.happinessql.domain.record.converter;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.record.domain.Location;
import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.dto.RecordRequestDto;
import com.hobak.happinessql.domain.user.domain.User;

public class RecordConverter {

    public static Record toRecord(RecordRequestDto recordRequestDto, User user, Activity activity) {
        return Record.builder()
                .happiness(recordRequestDto.getHappiness())
                .memo(recordRequestDto.getMemo())
                .user(user)
                .activity(activity)
                .build();
    }

    public static Location toLocation(RecordRequestDto recordRequestDto, Record record) {
        return Location.builder()
                .fullName(recordRequestDto.getFull_name())
                .city(recordRequestDto.getCity())
                .country(recordRequestDto.getCountry())
                .district(recordRequestDto.getDistrict())
                .xPos(recordRequestDto.getX_pos())
                .yPos(recordRequestDto.getY_pos())
                .record(record)
                .build();
    }


}
