package de.cogame.messageservice.web.eventproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * WebClient, which manages http- communication between message-service and event-service
 */
@FeignClient(name = "event-service", url = "${event-service.ribbon.listOfServers}")
public interface EventServiceProxy {
    /**
     * Makes request to the event-service and
     * checks whether an event exists
     *
     * @param id event's id
     * @return Event if exists, or throws NotFoundException
     */
    @GetMapping("/events/{id}")
    Event existsEvent(@PathVariable String id);

    /**
     * Makes request to the event-service and
     * check if an user participates in an event
     */
    @GetMapping("/events/{eventId}/users/{userId}/participates")
    boolean participatesUser(@PathVariable String userId, @PathVariable String eventId);
}
