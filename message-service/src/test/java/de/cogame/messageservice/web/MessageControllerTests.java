package de.cogame.messageservice.web;

import de.cogame.messageservice.MessageServiceApplication;
import de.cogame.messageservice.initializr.MessageInitializr;
import de.cogame.messageservice.web.MessageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageController messageController;

    @Value("classpath:data/messages.json")
    Resource  messagesFile;
    @Value("classpath:data/message.json")
    Resource  messageFile;

    @Test
    public void shouldReturnMessagesOfEvent1() throws Exception {

        given(messageController.getMessages("1")).willReturn(MessageInitializr.getMessages());
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
        ResponseEntity<Object> r = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(r).when(messageController).createMessages(any());

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
}
