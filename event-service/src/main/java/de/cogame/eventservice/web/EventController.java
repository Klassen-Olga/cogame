package de.cogame.eventservice.web;

import de.cogame.eventservice.model.Event;
import de.cogame.eventservice.repository.EventRepository;
import de.cogame.globalhandler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class EventController {

    EventRepository eventRepository;

    @GetMapping("/")
    public String greeting(){
        return "Hello from event service!";
    }

    @GetMapping("/events")
    public List<Event> getEvents(){
        return eventRepository.findAll();
    }

    @GetMapping("/events/{id}")
    public Event getEvents(@PathVariable String id){

        return getEventOrThrowNotFoundException(id);
    }

    @GetMapping("/events/{id}/users")
    public List<String> getUsersOfEvent(@PathVariable String id){
        Event event=getEventOrThrowNotFoundException(id);

        return event.getParticipantsId();
    }
    @GetMapping("/events/{id}/messages")
    public List<String> getMessagesOfEvent(@PathVariable String id){
        Event event=getEventOrThrowNotFoundException(id);

        return event.getMessagesId();
    }

    @PostMapping("/events")
    public ResponseEntity<Object> createEvent(@Valid @RequestBody Event event){

        Event newEvent= eventRepository.save(event);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(newEvent.getId())
                .toUri();
       return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/events/{id}")
    public void getEvents(@RequestBody Event event){

        eventRepository.delete(event);
    }

    @PutMapping("/events")
    public void changeEvent(@Valid @RequestBody Event event){

        getEventOrThrowNotFoundException(event.getId());
        eventRepository.save(event);

    }

    public Event getEventOrThrowNotFoundException(String id){
        Optional<Event> event=eventRepository.findById(id);
        if (!event.isPresent()){
            throw new NotFoundException("Event with the id "+id+" does not exist");
        }
        return event.get();
    }

}
