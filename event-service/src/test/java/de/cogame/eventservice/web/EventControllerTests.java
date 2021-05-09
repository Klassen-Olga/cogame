package de.cogame.eventservice.web;

import de.cogame.eventservice.initializr.EventInitializr;
import de.cogame.eventservice.model.Event;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    EventController eventController;

    @Value("classpath:data/events.json")
    Resource eventsFile;

    @Value("classpath:data/event.json")
    Resource eventFile;

    @Test
    public void returnsHelloFromEventService() throws Exception {

        // when
        given(this.eventController.greeting()).willReturn("Hello from event service!");

        // then
        this.mvc.perform(get("/greeting"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from event service!"));

    }

    @Test
    public void getEventsShouldReturnEvents() throws Exception {
        Event event = EventInitializr.getEvent("1");
        Event event1 = EventInitializr.getEvent("2");
        List<Event> events = new ArrayList<>();
        String fileEvents=StreamUtils.copyToString(eventsFile.getInputStream(), Charset.defaultCharset());
        events.add(event);
        events.add(event1);

        //when
        given(this.eventController.getEvents()).willReturn(events);


        //then
        this.mvc.perform(get("/events").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(fileEvents));

    }

}
