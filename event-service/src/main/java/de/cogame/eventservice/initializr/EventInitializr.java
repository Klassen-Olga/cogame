package de.cogame.eventservice.initializr;

import de.cogame.eventservice.model.Activity;
import de.cogame.eventservice.model.Address;
import de.cogame.eventservice.model.Event;
import de.cogame.eventservice.model.Tool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventInitializr {
    public static Event getEvent(String id){
        List<Activity> activities=new LinkedList<>();
        activities.add(new Activity("TABLE", "Monopoly"));
        activities.add(new Activity("ACTIVE", "Twister"));

        Map<String, String> participants=new HashMap<>();
        participants.put("1", "Albert");
        participants.put("2", "Cup");



        List<Tool> tools=new LinkedList<>();
        tools.add(new Tool("Monopoly game", true));
        tools.add(new Tool("Twister game", false));
        Event event= new Event(id, "Friends evening", "A party where we are going to play both monopoly and twister",
                LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(12, 0)),
                new Address("Norweische Street 15", "Erfurt", "Germany"),
                activities, "608873cd2e22d5088f7cdb5d", participants, 4, tools, LocalDateTime.now());
        event.setCreatedAt( LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(12, 0)));
        return event;
    }
}
