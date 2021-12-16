package de.cogame.eventservice.repository;

import de.cogame.eventservice.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository which manages database operations of the Event model
 */
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findAllByCreatorUserId(String id);
}
