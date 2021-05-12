package de.cogame.eventservice.web.messageproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * WebClient, which manages http- communication between message-service and event-service
 */
@FeignClient(name = "message-service", url = "${message-service.ribbon.listOfServers}")
public interface MessageServiceProxy {

    /**
     * Makes request to the message-service and
     * returns all messages of the event with the certain id
     * @param id event's id
     * @return list of messages
     */
    @GetMapping("/events/{id}/messages")
    public List<Message> getMessages(@PathVariable String id);


}


