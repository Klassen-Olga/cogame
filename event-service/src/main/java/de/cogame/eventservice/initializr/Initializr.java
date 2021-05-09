package de.cogame.eventservice.initializr;


import de.cogame.eventservice.model.Activity;
import de.cogame.eventservice.model.Address;
import de.cogame.eventservice.model.Event;
import de.cogame.eventservice.model.Tool;
import de.cogame.eventservice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class Initializr {

    @Autowired
    EventRepository eventRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void go(){

        Event event=EventInitializr.getEvent("1");
        eventRepository.deleteAll();
        eventRepository.save(event);

    }
}
