package com.research.assistant.service;

import com.research.assistant.dto.GeminiResponse;
import com.research.assistant.dto.ResearchRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class ResearchService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public ResearchService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }


    public String getResearch(ResearchRequest request) {

        String prompt = buildPrompt(request);

        Map<String, Object> requestBody = Map.of(
                "model", "gemini-3.8-flash",
                "input", prompt
        );

        String response = webClient.post()
                .uri(geminiApiUrl)
                .header("x-goog-api-key", geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractTextFromResponse(response);
    }

    private String extractTextFromResponse(String response) {
        try {
            GeminiResponse geminiResponse =
                    objectMapper.readValue(response, GeminiResponse.class);

            if (geminiResponse.getSteps() == null ||
                    geminiResponse.getSteps().isEmpty()) {
                return "No response generated";
            }

            for (GeminiResponse.Step step : geminiResponse.getSteps()) {

                // We only want the model output, not the thought step
                if ("model_output".equals(step.getType())
                        && step.getContent() != null
                        && !step.getContent().isEmpty()) {

                    return step.getContent()
                            .getFirst()
                            .getText();
                }
            }

            return "No text generated";
        } catch (Exception e) {
            return "Error Parsing Response" + e.getMessage();
        }
    }

    private String buildPrompt(ResearchRequest request) {
        StringBuilder prompt = new StringBuilder();
        switch (request.getOperation().toLowerCase()) {
            case "summarize":
                prompt.append("Provide a clear and concise summary of the following text in the few sentences : \n\n");
                break;
            case "suggest":
                prompt.append("Based on the following content : suggest realted topics and further reading. Format the response with clear heading and bullet points: \n\n");
                break;
            default:
                throw new IllegalStateException("Unknown Operation : " + request.getOperation());
        }
        prompt.append(request.getContent());

        return prompt.toString();
    }
}