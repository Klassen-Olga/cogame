package de.cogame.eventservice.repository;

import de.cogame.eventservice.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository which manages database operations of the Event model
 */
public interface EventRepository extends MongoRepository<Event, String> {
}
