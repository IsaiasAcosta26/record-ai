package com.recordAI.record_ai.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuggestionMessageDto {
    private Long id;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
}
