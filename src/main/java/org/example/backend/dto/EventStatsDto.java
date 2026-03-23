package org.example.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventStatsDto {
    private int totalTasks;
    private int completedTasks;
    private double completionPercentage;
    private int participantCount;
    private long daysRemaining;
}
