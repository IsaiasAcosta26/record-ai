package com.recordAI.record_ai.dto;

import com.recordAI.record_ai.enums.CommunicationStyle;
import com.recordAI.record_ai.enums.Gender;
import com.recordAI.record_ai.enums.RelationshipStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    @NotBlank
    private String name;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    private CommunicationStyle communicationStyle;

    @Size(max = 100)
    private String location;

    @NotBlank
    @Size(max = 30)
    private String nickname;

    @NotNull
    private Gender gender;

    @NotNull
    private RelationshipStatus relationshipStatus;

    @Size(max = 500)
    private String goals;

    @Size(max = 500)
    private String interests;

    @Size(max = 1000)
    private String personalitySummary;

}
