package de.cogame.userservice.web.eventproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * WebClient, which manages http- communication between user-service and event-service
 */
@FeignClient(name = "event-service", url = "${event-service.ribbon.listOfServers}")
public interface EventServiceProxy {
    /**
     * Makes request to the event-service and gets all dates and times of the events he participates in
     *
     */
    @GetMapping("/events/users/{id}/eventsTime")
    List<LocalDateTime> getUsersEventDateTimes(@PathVariable String id);
    /**
     * Makes request to the event-service and deltes user from all events when an event should be deleted
     *
     */
    @DeleteMapping("/events/users/{userId}/fromAllEvents")
    void deleteUserFromEvents(@PathVariable String userId);
}
