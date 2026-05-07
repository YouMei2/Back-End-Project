package com.hehorhii.restful_api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// QuoteController provides endpoints for retrieving AI-generated quotes.
// This controller handles requests for motivational quotes in various languages.
@CrossOrigin(origins = "http://localhost:63342")
@RestController
public class QuoteController {

    private final AiService aiService;

    // Constructor injecting AiService dependency
    public QuoteController(AiService aiService) {
        this.aiService = aiService;
    }

    // Retrieves an AI-generated quote
    @GetMapping("/ai-quote")
    public String getQuote() {
        return aiService.generateQuote();
    }
}
