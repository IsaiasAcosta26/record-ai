package com.recordAI.record_ai.service;

import com.recordAI.record_ai.dto.MemoryRecordDto;
import com.recordAI.record_ai.mapper.MemoryRecordMapper;
import com.recordAI.record_ai.model.MemoryRecord;
import com.recordAI.record_ai.repository.MemoryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VoiceInputService {

    private final WebClient.Builder webClientBuilder;
    private final MemoryRecordRepository memoryRecordRepository;
    private final MemoryRecordMapper memoryRecordMapper;
    private final OpenAiService openAiService;

    @Value("${openai.api.key}")
    private String openAiKey;

    public MemoryRecordDto processAudio(MultipartFile file, Long userId) {
        try {
            byte[] audioBytes = file.getBytes();

            ByteArrayResource resource = new ByteArrayResource(audioBytes) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            // Llamar a Whisper para transcripción
            Map response = webClientBuilder.build()
                    .post()
                    .uri("https://api.openai.com/v1/audio/transcriptions")
                    .header("Authorization", "Bearer " + openAiKey)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData("file", resource)
                            .with("model", "whisper-1"))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            String transcribedText = (String) response.get("text");

            // Guardar el pensamiento
            MemoryRecord memory = MemoryRecord.builder()
                    .userId(userId)
                    .content(transcribedText)
                    .createdAt(LocalDateTime.now())
                    .build();

            memory = memoryRecordRepository.save(memory);

            // Análisis con GPT
            MemoryRecord finalMemory = memory;
            openAiService.analyzeText(transcribedText)
                    .subscribe(result -> {
                        finalMemory.setSummary(result.getSummary());
                        finalMemory.setEmotion(result.getEmotion());
                        finalMemory.setCategory(result.getCategory());
                        memoryRecordRepository.save(finalMemory);
                    });

            return memoryRecordMapper.toDto(memory);
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar el archivo de audio", e);
        }
    }
}
