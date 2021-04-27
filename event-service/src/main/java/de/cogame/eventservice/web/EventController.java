package de.cogame.eventservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @GetMapping("/")
    public String greeting(){
        return "Hello from event service!";
    }
}
