package com.example.hackathon.service;



import org.apache.commons.lang3.StringEscapeUtils;
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


    private String apiUrl = "https://api.openai.com/v1/completions";

    private String apiKey = "";

    public String getResponse(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String model = "gpt-3.5-turbo";  // укажите нужную модель здесь
            String escapedPrompt = StringEscapeUtils.escapeJson(prompt);

            // Используйте messages вместо простого prompt
            String json = """
{
  "model": "%s",
  "messages": [
    {"role": "system", "content": "You are a helpful assistant."},
    {"role": "user", "content": "%s"}
  ]
}
""".formatted(model, prompt);
            System.out.println("Sending JSON: " + json);


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
