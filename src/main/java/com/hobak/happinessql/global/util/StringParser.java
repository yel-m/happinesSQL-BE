package com.hobak.happinessql.global.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringParser {
    public static List<String> extractTags(String description)  {
        if(description == null || description.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(description.split(" "));
    }
}