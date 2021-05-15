package de.cogame.messageservice.web;

import de.cogame.messageservice.model.Message;
import de.cogame.messageservice.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
/**
 * Restapi controller for message-service module
 * Manages get, post, put and delete methods
 */
@RestController
@AllArgsConstructor
public class MessageController {

    MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting() {
        return "Hello from message service!";
    }


    /**
     * Should be accessed from event-service module
     * @param id of event, to which messages are assigned
     * @return a List of all message instances associated with the particular event
     */
    @GetMapping("/events/{id}/messages")
    public List<Message> getMessages(@PathVariable String id) {

        return messageRepository.findByEventId(id);
    }

    /**
     * Saves an message into the database and returns 201 created status code
     */
    @PostMapping("/messages")
    public ResponseEntity<Object> createMessages(@Valid @RequestBody Message message) {

        Message newMessage = messageRepository.save(message);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(newMessage.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
