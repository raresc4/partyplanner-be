package org.example.backend.mapper;

import org.example.backend.dto.EventDto;
import org.example.backend.models.Event;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {
    public Event toModel(EventDto eventDto) {
        return Event.builder()
                .id(eventDto.getId() != null ? eventDto.getId() : null)
                .title(eventDto.getTitle())
                .location(eventDto.getLocation())
                .admin(eventDto.getAdmin())
                .date(eventDto.getDate())
                .involvedUsers(eventDto.getInvolvedUsers())
                .tasks(eventDto.getTasks())
                .build();
    }

    public EventDto toDto(Event event) {
        List<String> involvedUsersWithAdmin = new ArrayList<>(event.getInvolvedUsers());
        if (!involvedUsersWithAdmin.contains(event.getAdmin())) {
            involvedUsersWithAdmin.add(event.getAdmin());
        }

        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .location(event.getLocation())
                .admin(event.getAdmin())
                .date(event.getDate())
                .involvedUsers(involvedUsersWithAdmin)
                .tasks(event.getTasks())
                .build();
    }
}
