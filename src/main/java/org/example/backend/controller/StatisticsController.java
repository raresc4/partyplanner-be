package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.EventStatsDto;
import org.example.backend.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/{title}")
    public ResponseEntity<EventStatsDto> getEventStats(@PathVariable String title) {
        return ResponseEntity.ok(statisticsService.getEventStats(title));
    }
}
