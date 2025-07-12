package com.recordAI.record_ai.controller;

import com.recordAI.record_ai.dto.SuggestionMessageDto;
import com.recordAI.record_ai.service.SuggestionMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
@RequiredArgsConstructor
public class SuggestionMessageController {

    private final SuggestionMessageService service;

    @GetMapping("/user/{userId}")
    public List<SuggestionMessageDto> getAllSuggestions(@PathVariable Long userId) {
        return service.findByUserId(userId);
    }

    @GetMapping("/today")
    public List<SuggestionMessageDto> getTodaySuggestions(@RequestParam Long userId) {
        return service.findTodaySuggestions(userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
