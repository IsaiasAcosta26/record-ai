package com.recordAI.record_ai.repository;


import com.recordAI.record_ai.model.MemoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MemoryRecordRepository extends JpaRepository<MemoryRecord, Long> {
    List<MemoryRecord> findTop5ByUserIdOrderByCreatedAtDesc(Long userId);
    List<MemoryRecord> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime date);

}
