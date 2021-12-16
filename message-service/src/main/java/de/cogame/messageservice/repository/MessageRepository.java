package de.cogame.messageservice.repository;

import de.cogame.messageservice.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository which manages database operations of the Message model
 */
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByEventId(String eventId);

    void deleteMessagesById(String id);

    void deleteMessagesByEventId(String id);
}
