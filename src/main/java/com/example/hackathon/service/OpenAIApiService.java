package com.example.hackathon.service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@Service
public class OpenAIApiService {

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    public String getResponse(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String json = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 150}";

            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<String> request = new HttpEntity<>(json, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return "Ошибка: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        }
    }
}
