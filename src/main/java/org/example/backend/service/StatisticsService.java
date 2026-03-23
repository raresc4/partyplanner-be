package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.EventStatsDto;
import org.example.backend.models.Event;
import org.example.backend.models.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final EventService eventService;

    public EventStatsDto getEventStats(String title) {
        Event event = eventService.getEvent(title);

        List<Task> tasks = event.getTasks() == null ? List.of() : event.getTasks();
        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream()
                .filter(task -> task.getProgress() == 100)
                .count();

        double completionPercentage = 0.0;
        if (totalTasks > 0) {
            completionPercentage = ((double) completedTasks / totalTasks) * 100;
        }

        List<String> involvedUsers = event.getInvolvedUsers() == null ? List.of() : event.getInvolvedUsers();
        // Participant count includes the admin + involved users
        int participantCount = involvedUsers.size() + 1;

        long daysRemaining = 0;
        if (event.getDate() != null) {
            LocalDate eventDate = event.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), eventDate);
        }

        return EventStatsDto.builder()
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .completionPercentage(completionPercentage)
                .participantCount(participantCount)
                .daysRemaining(daysRemaining)
                .build();
    }
}
