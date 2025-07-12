package com.recordAI.record_ai.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeeklySummaryDto {
    private Long id;
    private Long userId;
    private String emotionalSummary;
    private LocalDate weekStartDate;
}
