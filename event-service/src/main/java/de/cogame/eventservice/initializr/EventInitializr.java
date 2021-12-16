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

/**
 * Class will be used for event-api testing and db initialising, creates Event instance
 */
public class EventInitializr {

    /**
     * @param id which should habe the event, empty String will autogenerate the id
     * @return an initialized event
     */
    public static Event getEvent(String id) {
        List<Activity> activities = new LinkedList<>();
        activities.add(new Activity("TABLE", "Monopoly"));
        activities.add(new Activity("ACTIVE", "Twister"));

        Map<String, String> participants = new HashMap<>();
        participants.put("1", "Albert");
        participants.put("2", "Cup");


        List<Tool> tools = new LinkedList<>();
        tools.add(new Tool("Monopoly game", true));
        tools.add(new Tool("Twister game", false));
        Event event = new Event(id,
                "Friends evening",
                "A party where we are going to play both monopoly and twister",
                LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(12, 0)),
                4,
                "1",
                LocalDateTime.now(),
                new Address("Norweische Street 15", "Erfurt", "Germany"),
                activities,
                participants,
                tools);
        event.setCreatedAt(LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(12, 0)));
        return event;
    }
}
