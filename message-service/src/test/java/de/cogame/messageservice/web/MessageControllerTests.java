package de.cogame.messageservice.web;

import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.messageservice.initializr.MessageInitializr;
import de.cogame.messageservice.repository.MessageRepository;
import de.cogame.messageservice.service.MessageService;
import de.cogame.messageservice.web.eventproxy.Event;
import de.cogame.messageservice.web.eventproxy.EventServiceProxy;
import de.cogame.messageservice.web.userproxy.User;
import de.cogame.messageservice.web.userproxy.UserServiceProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Test class to mock rest methods without any spring context
 *
 * */
@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private UserServiceProxy userServiceProxy;
    @MockBean
    private EventServiceProxy eventServiceProxy;

    @Value("classpath:data/messages.json")
    Resource messagesFile;
    @Value("classpath:data/message.json")
    Resource messageFile;

    @Test
    public void shouldReturnMessagesOfEvent1() throws Exception {

        given(messageService.findByEventId("1")).willReturn(MessageInitializr.getMessages());
        String messages = StreamUtils.copyToString(messagesFile.getInputStream(), Charset.defaultCharset());

        this.mockMvc.perform(get("/events/1/messages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(messages));

    }

    @Test
    public void postMessagesAndGetCreatedStatusCode() throws Exception {

        // given
        String message = StreamUtils.copyToString(messageFile.getInputStream(), Charset.defaultCharset());
        given(eventServiceProxy.existsEvent("1")).willReturn(new Event("1"));
        given(userServiceProxy.existsUser("1")).willReturn(new User("1"));
        given(eventServiceProxy.participatesUser("1", "1")).willReturn(true);
        doReturn(MessageInitializr.getMessages().get(0)).when(messageService).save(any());

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .content(message)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andDo(print());

    }
    @Test
    public void postMessagesWithInvalidEventWillThrowNotFound() throws Exception {

        // given
        String message = StreamUtils.copyToString(messageFile.getInputStream(), Charset.defaultCharset());
        given(eventServiceProxy.existsEvent("1")).willThrow(new NotFoundException(""));
        given(userServiceProxy.existsUser("1")).willReturn(new User("1"));
        given(eventServiceProxy.participatesUser("1", "1")).willReturn(true);
        doReturn(MessageInitializr.getMessages().get(0)).when(messageService).save(any());

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .content(message)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());

    }
    @Test
    public void postMessagesWithInvalidUserWillThrowNotFound() throws Exception {

        // given
        String message = StreamUtils.copyToString(messageFile.getInputStream(), Charset.defaultCharset());
        given(eventServiceProxy.existsEvent("1")).willReturn(new Event("1"));
        given(userServiceProxy.existsUser("1")).willThrow(new NotFoundException(""));
        given(eventServiceProxy.participatesUser("1", "1")).willReturn(true);
        doReturn(MessageInitializr.getMessages().get(0)).when(messageService).save(any());

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .content(message)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());

    }
    @Test
    public void postMessagesWithUserWhoNotParticipatesInEvent() throws Exception {

        // given
        String message = StreamUtils.copyToString(messageFile.getInputStream(), Charset.defaultCharset());
        given(eventServiceProxy.existsEvent("1")).willReturn(new Event("1"));
        given(userServiceProxy.existsUser("1")).willReturn(new User("1"));
        given(eventServiceProxy.participatesUser("1", "1")).willReturn(false);
        doReturn(MessageInitializr.getMessages().get(0)).when(messageService).save(any());

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .content(message)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mockMvc.perform(request)
                .andExpect(status().isConflict())
                .andDo(print());

    }
}
