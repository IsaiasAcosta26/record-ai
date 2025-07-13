package com.recordAI.record_ai.controller;

import com.recordAI.record_ai.model.SuggestionMessage;
import com.recordAI.record_ai.repository.SuggestionMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/suggestion")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionMessageRepository repository;

    @GetMapping("/today")
    public List<SuggestionMessage> getTodaySuggestions(@RequestParam Long userId) {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59);
        return repository.findByUserIdAndCreatedAtBetween(userId, start, end);
    }

    @GetMapping("/history")
    public List<SuggestionMessage> getHistory(
            @RequestParam Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        if (from != null && to != null) {
            return repository.findByUserIdAndCreatedAtBetween(userId, from, to);
        }
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
