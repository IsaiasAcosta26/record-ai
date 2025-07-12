package com.recordAI.record_ai.controller;

import lombok.Data;

@Data
public class OpenAiAnalysisResponse {
    private String summary;
    private String emotion;
    private String category;
}
