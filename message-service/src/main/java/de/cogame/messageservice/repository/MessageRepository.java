package de.cogame.messageservice.repository;

import de.cogame.messageservice.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByEventId(String eventId);

}
