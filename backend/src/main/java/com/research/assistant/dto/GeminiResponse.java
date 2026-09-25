package com.research.assistant.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeminiResponse {

    private List<Step> steps;

    @Data
    public static class Step {
        private String type;
        private List<Content> content;
    }

    @Data
    public static class Content {
        private String text;
        private String type;
    }
}