package com.recordAI.record_ai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_habit_memory")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHabitMemory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String keyword;

    private LocalDateTime lastMentioned;

    private Integer frequency;
}

