package de.cogame.messageservice.initializr;

import de.cogame.messageservice.model.Message;
import de.cogame.messageservice.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class Initializr {

    MessageRepository messageRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void go(){

        Message message=new Message("",
                "1",
                "Name1",
                "1",
                "A great place" , LocalDateTime.now());
        Message message2=new Message("",
                "2",
                "Name2",
                "1",
                "A great place" , LocalDateTime.now());
        Message message3=new Message("",
                "3",
                "Name3",
                "1",
                "A great place" , LocalDateTime.now());
        messageRepository.save(message);
        messageRepository.save(message2);
        messageRepository.save(message3);


    }
}
