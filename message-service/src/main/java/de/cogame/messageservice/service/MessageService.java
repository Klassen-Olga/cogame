package de.cogame.messageservice.service;

import de.cogame.messageservice.model.Message;
import de.cogame.messageservice.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Services for the event resource
 */
@Service
@AllArgsConstructor
public class MessageService {
    MessageRepository messageRepository;

    public List<Message> findByEventId(String id) {
        return messageRepository.findByEventId(id);
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public void deleteByEventId(String id) {
        messageRepository.deleteMessagesByEventId(id);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}
