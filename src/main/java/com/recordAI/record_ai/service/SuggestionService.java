package com.recordAI.record_ai.service;

import com.recordAI.record_ai.mapper.WeeklySummaryMapper;
import com.recordAI.record_ai.model.MemoryRecord;
import com.recordAI.record_ai.model.SuggestionMessage;
import com.recordAI.record_ai.model.User;
import com.recordAI.record_ai.model.WeeklySummary;
import com.recordAI.record_ai.repository.MemoryRecordRepository;
import com.recordAI.record_ai.repository.SuggestionMessageRepository;
import com.recordAI.record_ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//@Profile("prod")
@Service
@RequiredArgsConstructor
public class SuggestionService {

    private final UserRepository userRepository;
    private final MemoryRecordRepository memoryRecordRepository;
    private final SuggestionMessageRepository suggestionMessageRepository;
    private final WeeklySummaryService weeklySummaryService;
    private final WeeklySummaryMapper summaryMapper;
    private final OpenAiService openAiService;
    private final HolidayClient holidayClient;

    @Scheduled(cron = "0 0 7 * * *")
    public void generateDailySuggestions() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            List<MemoryRecord> recent = memoryRecordRepository
                    .findTop5ByUserIdOrderByCreatedAtDesc(user.getId());

            StringBuilder context = new StringBuilder();
            context.append("Perfil del usuario: ").append(user.getPersonalitySummary()).append("\n");
            context.append("Metas: ").append(user.getGoals()).append("\n");
            context.append("Intereses: ").append(user.getInterests()).append("\n");
            context.append("Últimos pensamientos:\n");
            recent.forEach(r -> context.append("- ").append(r.getContent()).append("\n"));

            openAiService.generateSuggestions(context.toString())
                    .subscribe(tips -> tips.forEach(msg -> saveSuggestion(user.getId(), msg)));

            handleBirthdayMessages(user);

            List<HolidayClient.Holiday> holidays = holidayClient.getUpcomingHolidays("DO", 7);

            for (HolidayClient.Holiday h : holidays) {
                LocalDate date = LocalDate.parse(h.getDate().getIso());

                if (date.isEqual(LocalDate.now())) {
                    saveSuggestion(user.getId(), "🎊 Hoy es feriado por: " + h.getName() + ". " + h.getDescription());
                } else if (date.isEqual(LocalDate.now().plusDays(2))) {
                    saveSuggestion(user.getId(), "📅 En 2 días es feriado por: " + h.getName() + ". ¡Aprovecha para recargar energía!");
                } else if (date.isBefore(LocalDate.now().plusDays(7))) {
                    saveSuggestion(user.getId(), "📌 Esta semana es feriado: " + h.getName() + " el " + date + ".");
                }
            }
        }
    }

    private void handleBirthdayMessages(User user) {
        LocalDate today = LocalDate.now();
        LocalDate birthday = user.getBirthDate().withYear(today.getYear());

        long daysUntil = today.until(birthday).getDays();

        if (daysUntil == 30) {
            saveSuggestion(user.getId(), "📆 ¡Falta un mes para tu cumpleaños, " + user.getNickname() + "! ¿Qué metas quisieras cerrar antes?");
        } else if (daysUntil == 7) {
            saveSuggestion(user.getId(), "🎉 Ya casi es tu cumple, " + user.getNickname() + ". ¿Ya pensaste cómo celebrarlo?");
        } else if (daysUntil == 2) {
            saveSuggestion(user.getId(), "🎂 Solo faltan 2 días. ¡Prepara algo especial para ti!");
        } else if (daysUntil == 0) {
            saveSuggestion(user.getId(), "🎊 ¡Feliz cumpleaños, " + user.getNickname() + "! Hoy es todo sobre ti 💛");
        }
    }

    private void saveSuggestion(Long userId, String content) {
        SuggestionMessage message = SuggestionMessage.builder()
                .userId(userId)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        suggestionMessageRepository.save(message);
    }

    @Scheduled(cron = "0 0 9 * * SUN")
    public void generateWeeklySummary() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
            List<MemoryRecord> weekThoughts = memoryRecordRepository
                    .findByUserIdAndCreatedAtAfter(user.getId(), oneWeekAgo);

            if (weekThoughts.isEmpty()) continue;

            StringBuilder context = new StringBuilder();
            context.append("Pensamientos del usuario en la última semana:\n");
            weekThoughts.forEach(r -> context.append("- ").append(r.getContent()).append("\n"));

            openAiService.analyzeText(context.toString())
                    .subscribe(analysis -> {
                        String message = String.format(
                                "📊 Esta semana fue mayormente **%s**. Hablaste sobre **%s**. Resumen: %s",
                                analysis.getEmotion(), analysis.getCategory(), analysis.getSummary()
                        );

                        saveSuggestion(user.getId(), message);

                        WeeklySummary summary = WeeklySummary.builder()
                                .userId(user.getId())
                                .emotionalSummary(message)
                                .weekStartDate(LocalDate.now().minusDays(7))
                                .build();

                        weeklySummaryService.create(summaryMapper.toDto(summary));
                    });
        }
    }



}