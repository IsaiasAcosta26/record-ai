package com.recordAI.record_ai.service;

import com.recordAI.record_ai.dto.MemoryRecordDto;
import com.recordAI.record_ai.mapper.MemoryRecordMapper;
import com.recordAI.record_ai.model.MemoryRecord;
import com.recordAI.record_ai.repository.MemoryRecordRepository;
import com.recordAI.record_ai.service.base.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemoryRecordService extends BaseServiceImpl<MemoryRecord, MemoryRecordDto> {

    private final MemoryRecordRepository memoryRecordRepository;
    private final MemoryRecordMapper memoryRecordMapper;
    private final OpenAiService openAiService;

    public MemoryRecordService(
            MemoryRecordRepository repository,
            MemoryRecordMapper mapper,
            OpenAiService openAiService
    ) {
        super(repository, mapper);
        this.memoryRecordRepository = repository;
        this.memoryRecordMapper = mapper;
        this.openAiService = openAiService;
    }

    @Override
    public MemoryRecordDto create(MemoryRecordDto dto) {
        MemoryRecord memory = memoryRecordMapper.toEntity(dto);
        memory.setCreatedAt(LocalDateTime.now());

        memory = memoryRecordRepository.save(memory);
        final MemoryRecord finalMemory = memory;

        openAiService.analyzeText(memory.getContent())
                .subscribe(result -> {
                    finalMemory.setEmotion(result.getEmotion());
                    finalMemory.setSummary(result.getSummary());
                    finalMemory.setCategory(result.getCategory());
                    memoryRecordRepository.save(finalMemory);
                });

        return memoryRecordMapper.toDto(memory);
    }
}