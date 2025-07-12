package com.recordAI.record_ai.controller;

import com.recordAI.record_ai.dto.WeeklySummaryDto;
import com.recordAI.record_ai.service.WeeklySummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-summaries")
@RequiredArgsConstructor
public class WeeklySummaryController {

    private final WeeklySummaryService service;

    @GetMapping
    public List<WeeklySummaryDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public WeeklySummaryDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public WeeklySummaryDto create(@RequestBody WeeklySummaryDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public WeeklySummaryDto update(@PathVariable Long id, @RequestBody WeeklySummaryDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
