package de.cogame.messageservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.cogame.messageservice.initializr.MessageInitializr;
import de.cogame.messageservice.model.Message;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Test class to mock MessageController's post method validation
 *  without any spring context
 *
 * */
@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerValidationTests {

    private ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MessageController messageController;

    @Before
    public void before() {

        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

    @Test
    public void postMessageWithInvalidDataWillReturn400() throws Exception {

        // when
        Message message = MessageInitializr.getMessages().get(0);
        message.setEventId("");
        message.setUserName("");
        message.setUserId("");

        String jsonString = objectMapper.writeValueAsString(message);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
                .andExpect(content().string(containsString("Event id can not be empty")))
                .andExpect(content().string(containsString("User name can not be empty")))
                .andExpect(content().string(containsString("User id can not be empty")))
                .andDo(print());

    }

}
