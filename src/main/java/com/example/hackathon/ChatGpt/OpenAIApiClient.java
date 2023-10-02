package com.example.hackathon.ChatGpt;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
//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

//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
//hakatonApiKey
public class OpenAIApiClient {

    private static final String API_URL = "https://api.openai.com/v1/engines/https://api.openai.com/v1/chat/completions"; // URL может отличаться
    private static final String API_KEY = "";

    public String getResponse(String prompt) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + API_KEY);

            String json = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 150}";
            httpPost.setEntity(new StringEntity(json));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
