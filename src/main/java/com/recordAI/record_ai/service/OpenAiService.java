package com.recordAI.record_ai.service;


import com.recordAI.record_ai.controller.OpenAiAnalysisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Value("${openai.api.key}")
    private String openAiKey;

    private final WebClient.Builder webClientBuilder;

    public Mono<OpenAiAnalysisResponse> analyzeText(String content) {
        return webClientBuilder.build()
                .post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + openAiKey)
                .header("Content-Type", "application/json")
                .bodyValue(Map.of(
                        "model", "gpt-3.5-turbo",
                        "messages", List.of(
                                Map.of("role", "system", "content", """
                                    Eres un asistente emocional. Analiza el texto del usuario y responde en este formato exacto:
                                    summary: breve resumen en una línea
                                    emotion: emoción dominante (feliz, triste, ansioso, motivado, etc.)
                                    category: tema principal (trabajo, salud, familia, metas, etc.)
                                """),
                                Map.of("role", "user", "content", content)
                        ),
                        "temperature", 0.7
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var choices = (List<Map<String, Object>>) response.get("choices");
                    var message = (Map<String, Object>) choices.get(0).get("message");
                    var contentStr = message.get("content").toString();

                    // Parse manual del formato
                    var lines = contentStr.split("\n");
                    OpenAiAnalysisResponse result = new OpenAiAnalysisResponse();
                    for (String line : lines) {
                        if (line.startsWith("summary:"))
                            result.setSummary(line.replace("summary:", "").trim());
                        if (line.startsWith("emotion:"))
                            result.setEmotion(line.replace("emotion:", "").trim());
                        if (line.startsWith("category:"))
                            result.setCategory(line.replace("category:", "").trim());
                    }
                    return result;
                });
    }

    public Mono<List<String>> generateSuggestions(String context) {
        return webClientBuilder.build()
                .post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + openAiKey)
                .bodyValue(Map.of(
                        "model", "gpt-3.5-turbo",
                        "messages", List.of(
                                Map.of("role", "system", "content", """
                                Eres un asistente emocional. Basado en el siguiente contexto,
                                genera 3 mensajes cortos para motivar, guiar o recordar cosas al usuario.
                                Devuélvelos en formato:
                                1. mensaje uno
                                2. mensaje dos
                                3. mensaje tres
                            """),
                                Map.of("role", "user", "content", context)
                        ),
                        "temperature", 0.7
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var choices = (List<Map<String, Object>>) response.get("choices");
                    var message = (Map<String, Object>) choices.get(0).get("message");
                    var contentStr = message.get("content").toString();

                    // Separar por línea y limpiar
                    return List.of(contentStr.split("\\n"))
                            .stream()
                            .map(String::trim)
                            .filter(line -> !line.isBlank())
                            .toList();
                });

    }

}
