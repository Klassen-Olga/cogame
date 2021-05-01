package de.cogame.messageservice.web;

import de.cogame.messageservice.model.Message;
import de.cogame.messageservice.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class MessageController {
    @GetMapping("/")
    public String greeting(){
        return "Hello from message service!";
    }

    MessageRepository messageRepository;

    @GetMapping("/events/{id}/messages")
    public List<Message> getMessages(@PathVariable String id){



        return messageRepository.findByEventId(id);
    }

}
