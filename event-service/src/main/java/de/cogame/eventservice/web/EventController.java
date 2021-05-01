package de.cogame.eventservice.web;

import de.cogame.eventservice.model.Event;
import de.cogame.eventservice.repository.EventRepository;
import de.cogame.globalhandler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        Optional<Event> event=eventRepository.findById(id);
        if (!event.isPresent()){
            throw new NotFoundException("Event with the id "+id+" does not exist");
        }
        return event.get();
    }

}
