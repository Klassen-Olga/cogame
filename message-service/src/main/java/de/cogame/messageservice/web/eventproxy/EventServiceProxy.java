package de.cogame.messageservice.web.eventproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "event-service", url = "${event-service.ribbon.listOfServers}")
public interface EventServiceProxy {

    @GetMapping("/events/{id}")
    Event existsEvent(@PathVariable String id);

    @GetMapping("/events/{eventId}/users/{userId}/participates")
    boolean participatesUser(@PathVariable String userId,@PathVariable String eventId);
}
