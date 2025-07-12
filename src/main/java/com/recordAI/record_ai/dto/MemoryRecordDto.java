package com.recordAI.record_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemoryRecordDto {

    @NotNull
    private Long userId;

    @NotBlank
    @Size(min = 10, max = 2000)
    private String content;
}
