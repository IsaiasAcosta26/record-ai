package com.recordAI.record_ai.controller;

import com.recordAI.record_ai.dto.UserDto;
import com.recordAI.record_ai.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

    private final UserService userService;

    @PostMapping
    public UserDto registerUser(@Valid @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }
}
