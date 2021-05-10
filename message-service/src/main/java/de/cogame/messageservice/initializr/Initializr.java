package de.cogame.messageservice.initializr;

import de.cogame.messageservice.model.Message;
import de.cogame.messageservice.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Component
public class Initializr {

    MessageRepository messageRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void go(){
        List<Message> messages=MessageInitializr.getMessages();
        messageRepository.deleteAll();
        messageRepository.save(messages.get(0));
        messageRepository.save(messages.get(1));
        messageRepository.save(messages.get(2));


    }
}
