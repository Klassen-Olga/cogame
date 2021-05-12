package de.cogame.eventservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.cogame.eventservice.initializr.EventInitializr;
import de.cogame.eventservice.model.Address;
import de.cogame.eventservice.model.Event;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Test class to mock EventController's post method validation
 *  without any spring context
 *
 * */
@Log4j2
@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventControllerValidationTests {

    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @MockBean
    EventController eventController;


    @Before
    public void before() {

        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

    @Test
    public void postEventWithInvalidNameWillReturn400() throws Exception {

        // when
        Event event = EventInitializr.getEvent("");
        event.setName("");

        //then
        testValidationForPostMethod("Name should have at least 3 characters", event);

    }

    @Test
    public void postEventWithInvalidDateTimeOfEventWillReturn400() throws Exception {

        // when
        Event event = EventInitializr.getEvent("");
        event.setDateTimeOfEvent(LocalDateTime.of(2000, 05, 04, 12, 00));

        //then
        testValidationForPostMethod("Date and time of the event should be in the future", event);

    }

    @Test
    public void postEventWithInvalidAddressWillReturn400() throws Exception {

        // when
        Event event = EventInitializr.getEvent("");
        event.setPlaceAddress(new Address("", "", ""));

        //then
        testValidationForPostMethod("City should have at least 3 characters", event);


    }

    @Test
    public void postEventWithInvalidNumberOfActivitiesWillReturn400() throws Exception {

        // when
        Event event = EventInitializr.getEvent("");
        event.setActivities(new ArrayList<>());

        //then
        testValidationForPostMethod("At least 1 activity required", event);


    }

    @Test
    public void postEventWithInvalidActivityWillReturn400() throws Exception {

        // when
        Event event = EventInitializr.getEvent("");
        event.getActivities().get(0).setActivityArt("flying");

        //then
        testValidationForPostMethod("Allowed values: TABLE, ACTIVE, COMPUTER", event);


    }

    @Test
    public void postEventWithInvalidCreatorIdWillReturn400() throws Exception {

        // when
        Event event = EventInitializr.getEvent("");
        event.setCreatorUserId("");

        //then
        testValidationForPostMethod("Creator id should not be empty", event);


    }


    void testValidationForPostMethod(String message, Event event) throws Exception {
        String jsonString = objectMapper.writeValueAsString(event);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/events")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
                .andExpect(content().string(containsString(message)))
                .andDo(print());

    }
}
