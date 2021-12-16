package de.cogame.userservice.web.eventproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "event-service", url = "${event-service.ribbon.listOfServers}")
public interface EventServiceProxy {

    @GetMapping("/events/users/{id}/eventsTime")
    List<LocalDateTime> getUsersEventDateTimes(@PathVariable String id);

    @DeleteMapping("/events/users/{userId}/fromAllEvents")
    void deleteUserFromEvents( @PathVariable String userId);
}
