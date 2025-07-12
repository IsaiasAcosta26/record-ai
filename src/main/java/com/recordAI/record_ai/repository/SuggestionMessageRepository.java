package com.recordAI.record_ai.repository;

import com.recordAI.record_ai.model.SuggestionMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SuggestionMessageRepository extends JpaRepository<SuggestionMessage, Long> {
    List<SuggestionMessage> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);
    List<SuggestionMessage> findByUserIdOrderByCreatedAtDesc(Long userId);

}
