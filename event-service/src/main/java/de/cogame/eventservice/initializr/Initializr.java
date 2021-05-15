package de.cogame.eventservice.initializr;


import de.cogame.eventservice.model.Event;
import de.cogame.eventservice.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Inserts a event seed into database eventdb on the start of the server
 *
 */
@AllArgsConstructor
@Component
public class Initializr {

    EventRepository eventRepository;

    /**
     * removes all created events and inserts an event with the id 1
     */
    @EventListener(ApplicationReadyEvent.class)
    public void go() {

        Event event = EventInitializr.getEvent("1");
        eventRepository.deleteAll();
        eventRepository.save(event);

    }
}
