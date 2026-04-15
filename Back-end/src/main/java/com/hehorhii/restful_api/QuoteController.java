package com.hehorhii.restful_api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*")
@RestController
public class QuoteController {

    private final AiService aiService;

    public QuoteController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/ai-quote")
    public String getAiQuote() {
        return aiService.generateQuote();
    }
}
