package contract;

import de.cogame.messageservice.MessageServiceApplication;
import de.cogame.messageservice.model.Message;
import de.cogame.messageservice.web.MessageController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)

@DirtiesContext
@AutoConfigureMessageVerifier
@SpringBootTest(classes = MessageServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BaseClass {

    @Autowired
    private MessageController messageController;

    /*@BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(messageController);

        Mockito.when(messageController.getMessage("1"))
                .thenReturn(new Message("1", "1", "Albert", "1", "Great event", LocalDateTime.now()));
    }*/

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder
                = MockMvcBuilders.standaloneSetup(messageController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}
