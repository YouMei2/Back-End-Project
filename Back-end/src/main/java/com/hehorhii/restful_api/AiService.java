package com.hehorhii.restful_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class AiService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    public String generateQuote() {
        RestTemplate restTemplate = new RestTemplate();

        // Настраиваем заголовки
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Формируем тело запроса (JSON)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama-3.1-8b-instant"); // Актуальная модель

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "Ты — мотивационный коуч. Напиши одну короткую, мощную цитату на русском языке. Только текст цитаты, без кавычек и лишних слов."));
        messages.add(Map.of("role", "user", "content", "Дай мотивацию на сегодня."));

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

            // Парсим ответ (Groq возвращает структуру как у OpenAI)
            List choices = (List) response.getBody().get("choices");
            Map firstChoice = (Map) choices.get(0);
            Map message = (Map) firstChoice.get("message");

            return message.get("content").toString();
        } catch (Exception e) {
            System.err.println("Ошибка AI: " + e.getMessage());
            return "Дисциплина — это мост между целями и достижениями."; // Фолбэк
        }
    }
}