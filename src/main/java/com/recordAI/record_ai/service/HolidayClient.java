package com.recordAI.record_ai.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayClient {

    @Value("${calendarific.api.key}")
    private String apiKey;

    public List<Holiday> getHolidays(String country, int year) {
        String url = "https://calendarific.com/api/v2/holidays"
                + "?api_key=" + apiKey
                + "&country=" + country
                + "&year=" + year;

        RestTemplate restTemplate = new RestTemplate();
        CalendarificResponse response = restTemplate.getForObject(url, CalendarificResponse.class);
        return response.getResponse().getHolidays();
    }

    public List<Holiday> getUpcomingHolidays(String country, int daysAhead) {
        LocalDate now = LocalDate.now();
        return getHolidays(country, now.getYear()).stream()
                .filter(h -> {
                    LocalDate holidayDate = LocalDate.parse(h.getDate().getIso());
                    return !holidayDate.isBefore(now) && !holidayDate.isAfter(now.plusDays(daysAhead));
                })
                .collect(Collectors.toList());
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CalendarificResponse {
        private HolidayData response;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HolidayData {
        private List<Holiday> holidays;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Holiday {
        private String name;
        private String description;
        private String type;
        private HolidayDate date;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HolidayDate {
        private String iso;
    }
}