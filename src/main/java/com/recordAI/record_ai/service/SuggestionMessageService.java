package com.recordAI.record_ai.service;

import com.recordAI.record_ai.dto.SuggestionMessageDto;
import com.recordAI.record_ai.mapper.SuggestionMessageMapper;
import com.recordAI.record_ai.model.SuggestionMessage;
import com.recordAI.record_ai.repository.SuggestionMessageRepository;
import com.recordAI.record_ai.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionMessageService extends BaseServiceImpl<SuggestionMessage, SuggestionMessageDto> {

    private final SuggestionMessageRepository repository;
    private final SuggestionMessageMapper mapper;

    public SuggestionMessageService(SuggestionMessageRepository repository, SuggestionMessageMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SuggestionMessageDto> findByUserId(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SuggestionMessageDto> findTodaySuggestions(Long userId) {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59);
        return repository.findByUserIdAndCreatedAtBetween(userId, start, end)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}

