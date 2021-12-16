package de.cogame.eventservice.service;

import de.cogame.eventservice.model.Event;
import de.cogame.eventservice.repository.EventRepository;
import de.cogame.globalhandler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Services for the event resource
 */
@Service
@AllArgsConstructor
public class EventService {

    EventRepository eventRepository;

    public List<Event> findAll() {

        return eventRepository.findAll();
    }

    public Event getEvent(String id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event with id " + id + " does not exist"));
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public void deleteById(String id) {
        eventRepository.deleteById(id);
    }

    public List<Event> findAllByCreatorUserId(String id) {
        return eventRepository.findAllByCreatorUserId(id);
    }
}
