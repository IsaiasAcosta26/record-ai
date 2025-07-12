package com.recordAI.record_ai.repository;

import com.recordAI.record_ai.model.WeeklySummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklySummaryRepository extends JpaRepository<WeeklySummary, Long> {
    List<WeeklySummary> findByUserId(Long userId);
}
