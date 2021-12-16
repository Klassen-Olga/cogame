package de.cogame.eventservice.web;

import de.cogame.eventservice.model.Event;
import de.cogame.eventservice.service.EventService;
import de.cogame.eventservice.web.messageproxy.Message;
import de.cogame.eventservice.web.messageproxy.MessageServiceProxy;
import de.cogame.eventservice.web.userproxy.UserServiceProxy;
import de.cogame.globalhandler.exception.EventConstraintViolation;
import de.cogame.globalhandler.exception.NumberOfParticipantsReached;
import de.cogame.globalhandler.exception.ServiceUnavailable;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Restapi controller for event-service module
 * Manages get, post, put and delete methods
 */
@Log4j2
@AllArgsConstructor
@RestController
public class EventController {

    EventService eventService;
    MessageServiceProxy messageServiceProxy;
    UserServiceProxy userServiceProxy;


    @GetMapping("/events")
    public List<Event> getEvents() {
        return eventService.findAll();
    }


    @GetMapping("/events/{id}")
    public Event getEvent(@PathVariable String id) {

        return eventService.getEvent(id);
    }

    /**
     * Makes a GET request to the message-service module and picks all messages of the event
     *
     * @param id of event which messages are required
     * @return a list with messages
     */
    @GetMapping("/events/{id}/messages")
    @CircuitBreaker(name = "message-service", fallbackMethod = "serviceUnreachable")
    public List<Message> getMessagesOfEvent(@PathVariable String id) {
        log.info("Sample API call received");
        eventService.getEvent(id);
        return messageServiceProxy.getMessages(id);
    }

    /**
     * @param id of event which users are required
     * @return a map of the users with key user-id and value user-name
     */
    @GetMapping("/events/{id}/users")
    public Map<String, String> getUsersOfEvent(@PathVariable String id) {

        Event event = eventService.getEvent(id);
        return event.getParticipants();
    }

    /**
     * Saves an event into the database and returns 201 created status code
     * calls user-service to check whether the user exists
     */
    @PostMapping("/events")
    @Retry(name = "default", fallbackMethod = "retryFallbackCreateEvent")
    @CircuitBreaker(name = "default", fallbackMethod = "circuitBreakerFallbackCreateEvent")
    public ResponseEntity<Object> createEvent(@Valid @RequestBody Event event) {
        //user existence check
        userServiceProxy.existsUser(event.getCreatorUserId());

        Event newEvent = eventService.save(event);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(newEvent.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    /**
     * Deletes event
     * calls message-service to delete all event's messages
     */
    @DeleteMapping("/events/{id}")
    @CircuitBreaker(name = "message-service", fallbackMethod = "circuitBreakerFallbackDeleteEvent")
    public void deleteEvent(@PathVariable String id, @RequestParam String creatorId) {
        // check if event exists
        Event event = getEvent(id);
        //check if creator is creator of event
        if (!event.getCreatorUserId().equals(creatorId)) {
            throw new EventConstraintViolation("User with id " + creatorId + " is not the creator of event with id " + id);
        }
        // delete messages
        messageServiceProxy.deleteMessages(id);
        // delete event
        eventService.deleteById(id);
    }


    /*
     * Adds an user to the existing event
     * Request format for the request body  {"id":"1", "name":"myName"}
     * calls user-service to check whether the user exists
     * */
    @PutMapping("/events/{eventId}/users")
    @CircuitBreaker(name = "default", fallbackMethod = "addUserFallback")
    public void addUser(@Valid @RequestParam
                                String userId, @RequestParam String userName, @PathVariable String eventId) {

        //check if event exists
        Event event = eventService.getEvent(eventId);

        //check if user exists
        userServiceProxy.existsUser(userId);
        //check if user already participates
        if (event.getParticipants().get(userId) != null) {
            throw new EventConstraintViolation("This user already participates in this event");
        }
        //check if participant number not reached
        if (event.getMaxParticipantsNumber() == (event.getParticipants().size())) {
            throw new NumberOfParticipantsReached("Maximum number of participants " + event.getMaxParticipantsNumber() + " reached");
        }
        //add new participant to event
        event.getParticipants().put(userId, userName);

        //save
        eventService.save(event);
    }


    /*
     * Removes an user from the existing event
     * calls user-service to check whether the user exists
     * */
    @CircuitBreaker(name = "default", fallbackMethod = "deleteUserFallback")
    @PutMapping("/events/{eventId}/users/{userId}")
    public void deleteUser(@PathVariable String userId, @PathVariable String eventId) {

        //check if event exists
        Event event = eventService.getEvent(eventId);

        //check if user exists
        userServiceProxy.existsUser(userId);

        //check if user to remove is not creator
        if (userId.equals(event.getCreatorUserId())) {
            throw new EventConstraintViolation("Creator of event can not be deleted");
        }
        //check if user participates in event         //remove participant from event
        if (event.getParticipants().remove(userId) == null) {
            throw new EventConstraintViolation("User with is " + userId + " does not participates in event with id " + eventId);
        }

        //update event
        eventService.save(event);
    }

    /**
     * is used to check if an user has events in the future
     * endpoint is used by user-service
     */
    @GetMapping("/events/users/{id}/eventsTime")
    List<LocalDateTime> getEventsDateTimes(@PathVariable String id) {
        List<LocalDateTime> localDateTimes = new LinkedList<>();
        eventService.findAllByCreatorUserId(id).forEach(event -> localDateTimes.add(event.getDateTimeOfEvent()));
        return localDateTimes;
    }

    /**
     * deletes user from all events
     * is called by user-service when an user should be deleted
     */
    @DeleteMapping("/events/users/{userId}/fromAllEvents")
    void deleteUserFromEvents(@PathVariable String userId) {
        List<Event> all = eventService.findAll();
        all.forEach(event -> {
                    event.getParticipants().remove(userId);
                    eventService.save(event);
                }
        );
    }

    /**
     * returns true if an user participates in an event
     */
    @GetMapping("/events/{eventId}/users/{userId}/participates")
    boolean participatesUser(@PathVariable String userId, @PathVariable String eventId) {
        Event event = eventService.getEvent(eventId);
        return event.getParticipants().get(userId) != null;
    }

    //fallbacks used by CircuitBreaker and Retry tools

    public List<Message> serviceUnreachable(FeignException ex) {
        throw new ServiceUnavailable("Message service unreachable, please return later");
    }

    public List<Message> retryFallback(FeignException ex) {
        throw new ServiceUnavailable("Message service unreachable");

    }

    public ResponseEntity<Object> retryFallbackCreateEvent(Event event, FeignException ex) {
        if (ex.status() == 404) {
            throw ex;
        }
        throw new ServiceUnavailable("User service unreachable");
    }

    public ResponseEntity<Object> circuitBreakerFallbackCreateEvent(Event event, FeignException ex) {
        if (ex.status() == 404) {
            throw ex;
        }
        throw new ServiceUnavailable("User service unreachable");
    }

    public void circuitBreakerFallbackDeleteEvent(@PathVariable String id, @RequestParam String creatorId, FeignException ex) {
        fallback(ex, "Message service unreachable");
    }

    public void addUserFallback(String userId, String userName, String eventId, FeignException ex) {
        fallback(ex, "User service unreachable");
    }

    public void deleteUserFallback(String userId, String eventId, FeignException ex) {
        fallback(ex, "User service unreachable");
    }

    public void fallback(FeignException ex, String message) {
        if (ex.status() == 404) {
            throw ex;
        }
        throw new ServiceUnavailable(message);
    }
}
