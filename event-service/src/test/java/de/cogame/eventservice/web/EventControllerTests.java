package de.cogame.eventservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.cogame.eventservice.initializr.EventInitializr;
import de.cogame.eventservice.model.Event;
import de.cogame.eventservice.service.EventService;
import de.cogame.eventservice.web.messageproxy.Message;
import de.cogame.eventservice.web.messageproxy.MessageServiceProxy;
import de.cogame.eventservice.web.userproxy.User;
import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.eventservice.web.userproxy.UserServiceProxy;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Test class to mock rest methods without any spring context
 *
 * */
@Log4j2
@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    EventService eventService;
    @MockBean
    MessageServiceProxy messageServiceProxy;
    @MockBean
    UserServiceProxy userServiceProxy;

    @Value("classpath:data/events.json")
    Resource eventsFile;

    @Value("classpath:data/event.json")
    Resource eventFile;

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();


    @Test
    public void getEventsShouldReturn2Events() throws Exception {
        Event event = EventInitializr.getEvent("1");
        Event event1 = EventInitializr.getEvent("2");
        List<Event> events = new ArrayList<>();
        String fileEvents = StreamUtils.copyToString(eventsFile.getInputStream(), Charset.defaultCharset());
        events.add(event);
        events.add(event1);

        //when
        given(this.eventService.findAll()).willReturn(events);


        //then
        this.mvc.perform(get("/events").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(fileEvents));

    }
    @Test
    public void getEventsShouldReturnEmptyList() throws Exception {

        //when
        given(this.eventService.findAll()).willReturn(new ArrayList<>());


        //then
        this.mvc.perform(get("/events").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }
    @Test
    public void getEventShouldThrowNotFoundException() throws Exception {

        //when
        given(this.eventService.getEvent("11")).willThrow(new NotFoundException("Event with the id 11 does not exist"));


        //then
        this.mvc.perform(get("/events/11").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Event with the id 11 does not exist")));


    }
    @Test
    public void getMessagesOfEventShouldReturnMessageWithId1() throws Exception {

        Message message = new Message("1", "1", "albert", "1", "texttext", LocalDateTime.now());
        LinkedList<Message> messages = new LinkedList<>();
        messages.push(message);
        //when
        given(this.eventService.getEvent("1")).willReturn(EventInitializr.getEvent("1"));
        given(this.messageServiceProxy.getMessages("1")).willReturn(messages);


        //then
        this.mvc.perform(get("/events/1/messages").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    public void getUsersOfEventShouldReturn2Users() throws Exception {
        Event event = EventInitializr.getEvent("1");
        String participants = ow.writeValueAsString(event.getParticipants());


        //when
        given(this.eventService.getEvent("1")).willReturn(EventInitializr.getEvent("1"));

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
        given(userServiceProxy.existsUser("1")).willReturn(new User("1"));
        doReturn(EventInitializr.getEvent("1")).when(eventService).save(any());
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
    public void postEventWithInvalidUserThrowsNotFound() throws Exception {

        // given
        String eventFromFile = StreamUtils.copyToString(eventFile.getInputStream(), Charset.defaultCharset());
        given(userServiceProxy.existsUser("1")).willThrow(new NotFoundException(""));
        doReturn(EventInitializr.getEvent("1")).when(eventService).save(any());
        // when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/events")
                .accept(MediaType.APPLICATION_JSON)
                .content(eventFromFile)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());

    }
    @Test
    public void deleteExistingEventWillReturnOk() throws Exception {

        // given
        doReturn(EventInitializr.getEvent("1")).when(eventService).getEvent("1");
        doNothing().when(messageServiceProxy).deleteMessages("1");
        doNothing().when(eventService).deleteById("1");


        // when
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/events/1?creatorId=1")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    public void deleteNotExistingEventWillThrowNotFoundException() throws Exception {

        // given
        doReturn(EventInitializr.getEvent("1")).when(eventService).getEvent("1");
        doNothing().when(messageServiceProxy).deleteMessages("1");
        doNothing().when(eventService).deleteById("1");


        // when
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/events/1?creatorId=12")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isConflict())
                .andDo(print());

    }
    @Test
    public void addUserWithNoConstraintViolatedReturnsOk() throws Exception {

        // given
        doReturn(EventInitializr.getEvent("1")).when(eventService).getEvent("1");
        doReturn(new User("1")).when(userServiceProxy).existsUser("1");
        doReturn(EventInitializr.getEvent("1")).when(eventService).save(any());


        // when
        RequestBuilder request = MockMvcRequestBuilders
                .put("/events/1/users?userName=albert&userId=11")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    public void addUserWhoAlreadyParticipatesReturnsConflict() throws Exception {

        // given
        doReturn(EventInitializr.getEvent("1")).when(eventService).getEvent("1");
        doReturn(new User("1")).when(userServiceProxy).existsUser("1");
        doReturn(EventInitializr.getEvent("1")).when(eventService).save(any());


        // when
        RequestBuilder request = MockMvcRequestBuilders
                .put("/events/1/users?userName=albert&userId=1")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isConflict())
                .andDo(print());

    }
    @Test
    public void addUserWhenParticipantsNumberIsReachedReturnsForbidden() throws Exception {
        Event event = EventInitializr.getEvent("1");
        event.setMaxParticipantsNumber(2);
        // given
        doReturn(event).when(eventService).getEvent("1");
        doReturn(new User("1")).when(userServiceProxy).existsUser("1");
        doReturn(EventInitializr.getEvent("1")).when(eventService).save(any());


        // when
        RequestBuilder request = MockMvcRequestBuilders
                .put("/events/1/users?userName=albert&userId=11")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(print());

    }
    @Test
    public void deleteParticipantReturnsOk() throws Exception {
        Event event = EventInitializr.getEvent("1");
        // given
        doReturn(event).when(eventService).getEvent("1");
        doReturn(new User("2")).when(userServiceProxy).existsUser("2");
        doReturn(EventInitializr.getEvent("1")).when(eventService).save(any());


        // when
        RequestBuilder request = MockMvcRequestBuilders
                .put("/events/1/users/2")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    public void deleteParticipantWhoIsCretorReturnsConflict() throws Exception {
        Event event = EventInitializr.getEvent("1");
        // given
        doReturn(event).when(eventService).getEvent("1");
        doReturn(new User("2")).when(userServiceProxy).existsUser("2");
        doReturn(EventInitializr.getEvent("1")).when(eventService).save(any());


        // when
        RequestBuilder request = MockMvcRequestBuilders
                .put("/events/1/users/1")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isConflict())
                .andDo(print());

    }
    @Test
    public void getDateTimesWhereUserParticipatesReturns20220101() throws Exception {
        Event event = EventInitializr.getEvent("1");
        List<Event> events = new LinkedList<>();
        events.add(event);
        LocalDateTime of = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(12, 0));
        List<LocalDateTime> localDateTimes = new LinkedList<>();
        localDateTimes.add(of);

        given(eventService.findAllByCreatorUserId("1")).willReturn(events);



        // when
        RequestBuilder request = MockMvcRequestBuilders
                .get("/events/users/1/eventsTime")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(content().json("["+"\""+of+":00"+"\""+"]"))
                .andExpect(status().isOk())
                .andDo(print());

    }


}
