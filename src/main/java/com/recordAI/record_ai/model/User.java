package com.recordAI.record_ai.model;

import com.recordAI.record_ai.enums.CommunicationStyle;
import com.recordAI.record_ai.enums.Gender;
import com.recordAI.record_ai.enums.RelationshipStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "app_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate birthDate;

    private String location;

    @Enumerated(EnumType.STRING)
    private CommunicationStyle communicationStyle;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private RelationshipStatus relationshipStatus;

    @Column(length = 30)
    private String nickname;

    @Column(length = 500)
    private String goals;

    @Column(length = 500)
    private String interests;

    @Column(length = 1000)
    private String personalitySummary;

}
