package de.cogame.eventservice.web.messageproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "message-service", url="${message-service.ribbon.listOfServers}")
public interface MessageServiceProxy {

    @GetMapping("/events/{id}/messages")
    public List<Message> getMessages(@PathVariable String id);


}


