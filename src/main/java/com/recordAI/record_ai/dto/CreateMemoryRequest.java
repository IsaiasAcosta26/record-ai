package com.recordAI.record_ai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMemoryRequest {
    @NotNull
    private Long userId;

    @NotNull
    private String content;
}
