package com.example.hackathon.service.Impl;



import com.example.hackathon.service.OpenAIApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OpenAIApiServiceImpl implements OpenAIApiService {


    private String apiUrl = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api.key}")
    private String apiKey;
    public void str(){
        System.out.println("\n\nthe key: "+apiKey);
    }

    @Override
    public String getResponse(String prompt) {

        System.out.println("\n\nthe key: "+apiKey);
        try {
            RestTemplate restTemplate = new RestTemplate();
            String model = "gpt-3.5-turbo";  // укажите нужную модель здесь

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("model", model);
            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", "You are a helpful assistant."));
            messages.add(Map.of("role", "user", "content", prompt));
            requestData.put("messages", messages);

            String json = objectMapper.writeValueAsString(requestData);

            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<String> request = new HttpEntity<>(json, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return "Ошибка: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
