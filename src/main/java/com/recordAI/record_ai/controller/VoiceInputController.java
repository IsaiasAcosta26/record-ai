package com.recordAI.record_ai.controller;

import com.recordAI.record_ai.dto.MemoryRecordDto;
import com.recordAI.record_ai.service.VoiceInputService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/voice")
@RequiredArgsConstructor
public class VoiceInputController {

    private final VoiceInputService voiceInputService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MemoryRecordDto processVoice(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) {
        return voiceInputService.processAudio(file, userId);
    }
}