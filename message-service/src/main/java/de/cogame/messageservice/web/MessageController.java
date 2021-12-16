package de.cogame.messageservice.web;

import de.cogame.globalhandler.exception.EventConstraintViolation;
import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.messageservice.model.Message;
import de.cogame.messageservice.repository.MessageRepository;
import de.cogame.messageservice.service.MessageService;
import de.cogame.messageservice.web.eventproxy.Event;
import de.cogame.messageservice.web.eventproxy.EventServiceProxy;
import de.cogame.messageservice.web.userproxy.UserServiceProxy;
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

    MessageService messageService;
    UserServiceProxy userServiceProxy;
    EventServiceProxy eventServiceProxy;


    /**
     * Should be accessed from event-service module
     *
     * @param id of event, to which messages are assigned
     * @return a List of all message instances associated with the particular event
     */
    @GetMapping("/events/{id}/messages")
    public List<Message> getMessagesOfEvent(@PathVariable String id) {
        // check if event exists
        eventServiceProxy.existsEvent(id);

        return messageService.findByEventId(id);
    }

    @GetMapping("/messages")
    public List<Message> getMessages() {

        return messageService.findAll();
    }

    @DeleteMapping("/events/{id}/messages")
    public void deleteEventsMessages(@PathVariable String id){
        messageService.deleteByEventId(id);
    }

    /**
     * Saves an message into the database and returns 201 created status code
     */
    @PostMapping("/messages")
    public ResponseEntity<Object> createMessage(@Valid @RequestBody Message message) {

        // check if event exists
        eventServiceProxy.existsEvent(message.getEventId());
        //check if user exists
        userServiceProxy.existsUser(message.getUserId());
        //check if user is in event
        if (!eventServiceProxy.participatesUser(message.getUserId(), message.getEventId())) {
            throw new EventConstraintViolation("User with id " + message.getUserId() + " does not participates in event with id " + message.getEventId());
        }
        //save message
        Message savedMessage = messageService.save(message);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(savedMessage.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }


}
