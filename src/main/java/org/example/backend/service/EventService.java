package org.example.backend.service;

import lombok.AllArgsConstructor;
import org.example.backend.exception.ConflictException;
import org.example.backend.exception.NotFoundException;
import org.example.backend.mapper.EventMapper;
import org.example.backend.models.Event;
import org.example.backend.models.Task;
import org.example.backend.repository.EventRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Event createEvent(Event event) {
        Optional<Event> existingEvent = eventRepository.findEventByTitle(event.getTitle());
        if(existingEvent.isPresent()) throw new ConflictException("Event already exists");
        if(userRepository.findUserByUsername(event.getAdmin()).isEmpty()) throw new ConflictException("Admin user does not exist");
        event.getInvolvedUsers().forEach(
                username -> {
                    if(userRepository.findUserByUsername(username).isEmpty()) {
                        throw new NotFoundException("Involved user " + username + " does not exist");
                    }
                }
        );
        Set<String> titles = event.getTasks().stream().map(Task::getTitle).collect(java.util.stream.Collectors.toSet());
        if(titles.size() != event.getTasks().size()) throw new ConflictException("Task titles must be unique");
        return eventRepository.save(event);
    }

    public Event getEvent(String title) {
        return eventRepository.findEventByTitle(title).orElseThrow(() -> new NotFoundException("Event not found"));
    }

    public List<Event> getUserEvents(String username) {
        if(userRepository.findUserByUsername(username).isEmpty()) throw new NotFoundException("User not found");
        return eventRepository.findByAdminOrInvolvedUsersContaining(username, username);
    }

    public Event updateEvent(Event event) {
        Optional<Event> existingEvent = eventRepository.findEventByTitle(event.getTitle());
        if(existingEvent.isEmpty()) throw new NotFoundException("Event not found");
        if(!existingEvent.get().getAdmin().equals(event.getAdmin())) throw new ConflictException("Only the admin can update the event");
        return eventRepository.save(event);
    }

    public void deleteEvent(String name) {
        Optional<Event> event = eventRepository.findEventByTitle(name);
        if(event.isEmpty()) throw new NotFoundException("Event not found");
        eventRepository.delete(event.get());
    }
}
