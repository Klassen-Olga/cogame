package de.cogame.eventservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.cogame.eventservice.initializr.EventInitializr;
import de.cogame.eventservice.model.Event;
import de.cogame.globalhandler.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

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
    public void getEventsShouldReturn2Events() throws Exception {
        Event event = EventInitializr.getEvent("1");
        Event event1 = EventInitializr.getEvent("2");
        List<Event> events = new ArrayList<>();
        String fileEvents = StreamUtils.copyToString(eventsFile.getInputStream(), Charset.defaultCharset());
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

    @Test
    public void getEventsShouldReturnEmptyList() throws Exception {

        //when
        given(this.eventController.getEvents()).willReturn(new ArrayList<>());


        //then
        this.mvc.perform(get("/events").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    public void getEventShouldThrowNotFoundException() throws Exception {

        //when
        given(this.eventController.getEvent("11")).willThrow(new NotFoundException("Event with the id 11 does not exist"));


        //then
        this.mvc.perform(get("/events/11").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Event with the id 11 does not exist")));


    }

    @Test
    public void getUsersOfEventShouldReturn2Users() throws Exception {
        Event event = EventInitializr.getEvent("1");
        String participants = ow.writeValueAsString(event.getParticipants());


        //when
        given(this.eventController.getUsersOfEvent("1")).willReturn(event.getParticipants());

        //then
        this.mvc.perform(get("/events/1/users").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(participants));

    }

    @Test
    public void postEventAndGetCreatedStatusCode() throws Exception {

        // given
        String eventFromFile = StreamUtils.copyToString(eventFile.getInputStream(), Charset.defaultCharset());
        ResponseEntity<Object> r = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(r).when(eventController).createEvent(any());

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/events")
                .accept(MediaType.APPLICATION_JSON)
                .content(eventFromFile)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    public void deleteExistingEventWillReturnOk() throws Exception {

        // given
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return null;
            }
        }).when(eventController).deleteEvent("1");


        // when
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/events/1")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    public void deleteNotExistingEventWillThrowNotFoundException() throws Exception {

        // given
        doThrow(NotFoundException.class).when(eventController).deleteEvent("222");



        // when
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/events/222")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void addUserWillReturn200StatusCode() throws Exception {

        Map<String, String> user=new HashMap<>();
        user.put("id", "12");
        user.put("name", "Truli");
        // given
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return null;
            }
        }).when(eventController).addUser(user, "1");

        String userJson = ow.writeValueAsString(user);
        // when
        RequestBuilder request = MockMvcRequestBuilders
                .put("/events/1/user")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

    }

}
