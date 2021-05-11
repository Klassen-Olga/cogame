package contract;

import de.cogame.messageservice.MessageServiceApplication;
import de.cogame.messageservice.model.Message;
import de.cogame.messageservice.repository.MessageRepository;
import de.cogame.messageservice.web.MessageController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/*
* Base class for spring contract-driven testing
* Describes rules for testing of MessageController and MessageRepository from event-service
* */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BaseClass {


    @Autowired
    private MessageController messageController;

    @MockBean
    private MessageRepository messageRepository;

    /*
    *
    * Sets up MackBuilder and creates mocks for message repository,
    * which should be used from event-service and other consumers
    * */
    @BeforeEach
    public void setup() {
       /*when(messageRepository.findById("33"))
                .thenReturn(Optional.of(new Message("33", "33", "John", "33", "Great Event", LocalDateTime.of(2023, 01, 01, 13, 00))));
*/
        List<Message> messages=new ArrayList<>();
        messages.add(new Message("21", "21", "Dom", "20", "Great Event, Dom", LocalDateTime.of(2023, 01, 01, 14, 00)));
        messages.add(new Message("22", "22", "Bom", "20", "Great Event, Bom", LocalDateTime.of(2023, 01, 01, 13, 00)));
        when(messageRepository.findByEventId("20"))
                .thenReturn(messages);


        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(messageController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
    @Test
    public void test(){

    }
}
