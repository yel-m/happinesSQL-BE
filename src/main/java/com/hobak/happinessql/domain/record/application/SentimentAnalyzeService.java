package com.hobak.happinessql.domain.record.application;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SentimentAnalyzeService {
    @Value("${sentiment.clientId}")
    private String clientId;

    @Value("${sentiment.clientSecret}")
    private String clientSecret;

    public Map<String, List<String>> getAnalyzeResult(String content) {
        String url = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-NCP-APIGW-API-KEY-ID", clientId);
            headers.add("X-NCP-APIGW-API-KEY", clientSecret);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("content", content);

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("sentences")) {
                List<Map<String, Object>> sentences = (List<Map<String, Object>>) responseBody.get("sentences");
                List<Map<String, String>> emotionText = sentences.stream()
                        .map(sentence -> Map.of(
                                "content", (String) sentence.get("content"),
                                "sentiment", (String) sentence.get("sentiment")
                        ))
                        .collect(Collectors.toList());

                return separateSentiment(emotionText);
            } else {
                log.warn("Response does not contain 'sentences' key");
                return Map.of("positive", List.of(), "negative", List.of());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("HTTP error: " + e.toString());
            return Map.of("positive", List.of(), "negative", List.of());
        } catch (Exception e) {
            log.error("Unexpected error: " + e.toString());
            return Map.of("positive", List.of(), "negative", List.of());
        }
    }

    private Map<String, List<String>> separateSentiment(List<Map<String, String>> emotionText) {
        Map<String, List<String>> groupedTexts = emotionText.stream()
                .collect(Collectors.groupingBy(
                        map -> map.get("sentiment"),
                        Collectors.mapping(map -> map.get("content"), Collectors.toList())
                ));

        List<String> positive = groupedTexts.getOrDefault("positive", List.of());
        List<String> negative = groupedTexts.getOrDefault("negative", List.of());

        return Map.of("positive", positive, "negative", negative);
    }
}