package com.hobak.happinessql.domain.report.converter;

import com.hobak.happinessql.domain.report.domain.AgeGroup;
import org.springframework.core.convert.converter.Converter;

public class AgeGroupConverter implements Converter<String, AgeGroup> {
    @Override
    public AgeGroup convert(String source) {
        return AgeGroup.from(source.toUpperCase());
    }
}