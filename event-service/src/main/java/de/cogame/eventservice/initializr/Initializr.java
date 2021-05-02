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
import java.util.LinkedList;
import java.util.List;

@Component
public class Initializr {

    @Autowired
    EventRepository eventRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void go(){

        List<Activity> activities=new LinkedList<>();
        activities.add(new Activity("TABLE", "Monopoly"));
        activities.add(new Activity("ACTIVE", "Twister"));

        List<String> participants=new LinkedList<>();
        participants.add("608873cd2e22d5088f7cdb5d");
        participants.add("608873cd2e22d5088f7cdb5e");


        List<Tool> tools=new LinkedList<>();
        tools.add(new Tool("Monopoly game", true));
        tools.add(new Tool("Twister game", false));

        Event event=new Event("1", "Friends evening", "A party where we are going to play both monopoly and twister",
                LocalDateTime.of(LocalDate.of(2021, 8, 15), LocalTime.of(12, 00)),
                new Address("Norweische Street 15", "Erfurt", "Germany"),
                activities, "608873cd2e22d5088f7cdb5d", participants, 4, tools, LocalDateTime.now());

        eventRepository.deleteAll();
        eventRepository.save(event);

    }
}
