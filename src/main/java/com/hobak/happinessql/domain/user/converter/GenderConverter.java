package com.hobak.happinessql.domain.user.converter;

import com.hobak.happinessql.domain.user.domain.Gender;
import org.springframework.core.convert.converter.Converter;

public class GenderConverter implements Converter<String, Gender> {
    @Override
    public Gender convert(String source) {
        return Gender.from(source.toUpperCase());
    }
}