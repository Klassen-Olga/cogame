package de.cogame.eventservice.web;

import de.cogame.eventservice.model.Event;
import de.cogame.eventservice.repository.EventRepository;
import de.cogame.eventservice.web.messageproxy.Message;
import de.cogame.eventservice.web.messageproxy.MessageServiceProxy;
import de.cogame.globalhandler.exception.NotFoundException;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class EventController {


    EventRepository eventRepository;
    MessageServiceProxy messageServiceProxy;


    @GetMapping("/greeting")
    public String greeting() {

        return "Hello from event service!";
    }

    @GetMapping("/events")
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/events/{id}")
    public Event getEvent(@PathVariable String id) {

        return getEventOrThrowNotFoundException(id);
    }

    @GetMapping("/events/{id}/messages")
    public List<Message> getMessagesOfEvent(@PathVariable String id) {

        getEventOrThrowNotFoundException(id);
        return messageServiceProxy.getMessages(id);
    }

    @GetMapping("/events/{id}/users")
    public Map<String, String> getUsersOfEvent(@PathVariable String id) {

        Event event = getEventOrThrowNotFoundException(id);
        return event.getParticipants();
    }

    @PostMapping("/events")
    public ResponseEntity<Object> createEvent(@Valid @RequestBody Event event) {

        Event newEvent = eventRepository.save(event);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(newEvent.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable String id) {

        getEventOrThrowNotFoundException(id);
        eventRepository.deleteById(id);
    }

    @PutMapping("/events")
    public void changeEvent(@Valid @RequestBody Event event) {

        getEventOrThrowNotFoundException(event.getId());
        eventRepository.save(event);

    }
    
    @PutMapping("/events/{eventId}/user")
    public void addUser(@Valid @RequestBody  @ApiParam(
            value = "Request format {\"id\":\"1\", \"name\":\"myName\"}"
    ) Map<String, String> user, @PathVariable String eventId) {

        Event event = getEventOrThrowNotFoundException(eventId);
        if (!user.get("id").isBlank()) {
            event.getParticipants().put(user.get("id"), user.get("name"));
            eventRepository.save(event);
        }

    }

    public Event getEventOrThrowNotFoundException(String id) {
        Optional<Event> event = eventRepository.findById(id);
        if (!event.isPresent()) {
            throw new NotFoundException("Event with the id " + id + " does not exist");
        }
        return event.get();
    }

}
