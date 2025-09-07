package org.example.backend.repository;

import org.example.backend.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {
    Optional<Event> findEventByTitle(String title);
    List<Event> findByAdminOrInvolvedUsersContaining(String admin, String involvedUser);
}
