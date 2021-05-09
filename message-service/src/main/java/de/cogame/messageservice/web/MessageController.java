package de.cogame.messageservice.web;

import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.messageservice.model.Message;
import de.cogame.messageservice.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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


    @DeleteMapping("/events/{id}/messages")
    public void deleteMessages(@PathVariable String id){
         messageRepository.deleteMessagesById(id);

    }
    @PostMapping("/messages")
    public ResponseEntity<Object> createMessages(@Valid @RequestBody Message message){

        Message newMessage= messageRepository.save(message);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(newMessage.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
