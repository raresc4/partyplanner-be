package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.EventDto;
import org.example.backend.mapper.EventMapper;
import org.example.backend.service.EventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class EventController {
    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping("/createEvent")
    public ResponseEntity<EventDto> createEvent(@RequestBody @Valid EventDto eventDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventMapper.toDto(eventService.createEvent(eventMapper.toModel(eventDto))));
    }

    @GetMapping("/{name}")
    public ResponseEntity<EventDto> getEvent(@PathVariable String name) {
        return ResponseEntity.ok(eventMapper.toDto(eventService.getEvent(name)));
    }

    @GetMapping("/getUserEvents/{username}")
    public ResponseEntity<List<EventDto>> getUserEvents(@PathVariable String username) {
       return ResponseEntity.ok(eventService.getUserEvents(username).stream().map(eventMapper::toDto).toList());
    }

    @PutMapping("/updateEvent")
    public ResponseEntity<EventDto> updateEvent(@RequestBody @Valid EventDto eventDto) {
        return ResponseEntity.ok(eventMapper.toDto(eventService.updateEvent(eventMapper.toModel(eventDto))));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String name) {
        eventService.deleteEvent(name);
        return ResponseEntity.noContent().build();
    }
}
