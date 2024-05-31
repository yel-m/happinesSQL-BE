package com.hobak.happinessql.global.config.web;

import com.hobak.happinessql.domain.report.converter.AgeGroupConverter;
import com.hobak.happinessql.domain.user.converter.GenderConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GenderConverter());
        registry.addConverter(new AgeGroupConverter());
    }
}