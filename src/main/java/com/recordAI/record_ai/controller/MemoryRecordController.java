package com.recordAI.record_ai.controller;

import com.recordAI.record_ai.dto.MemoryRecordDto;
import com.recordAI.record_ai.service.MemoryRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memories")
@RequiredArgsConstructor
public class MemoryRecordController {

    private final MemoryRecordService memoryRecordService;

    @GetMapping
    public List<MemoryRecordDto> findAll() {
        return memoryRecordService.findAll();
    }

    @GetMapping("/{id}")
    public MemoryRecordDto findById(@PathVariable Long id) {
        return memoryRecordService.findById(id);
    }

    @PostMapping
    public MemoryRecordDto create(@Valid @RequestBody MemoryRecordDto dto) {
        return memoryRecordService.create(dto); // GPT se aplica dentro del service
    }

    @PutMapping("/{id}")
    public MemoryRecordDto update(@PathVariable Long id, @Valid @RequestBody MemoryRecordDto dto) {
        return memoryRecordService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        memoryRecordService.delete(id);
    }
}