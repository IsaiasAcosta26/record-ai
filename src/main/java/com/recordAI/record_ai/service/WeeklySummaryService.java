package com.recordAI.record_ai.service;

import com.recordAI.record_ai.dto.WeeklySummaryDto;
import com.recordAI.record_ai.mapper.WeeklySummaryMapper;
import com.recordAI.record_ai.model.WeeklySummary;
import com.recordAI.record_ai.repository.WeeklySummaryRepository;
import com.recordAI.record_ai.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WeeklySummaryService extends BaseServiceImpl<WeeklySummary, WeeklySummaryDto> {

    public WeeklySummaryService(WeeklySummaryRepository repository, WeeklySummaryMapper mapper) {
        super(repository, mapper);
    }
}