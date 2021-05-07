package de.cogame.eventservice;


import de.cogame.eventservice.web.EventController;
import de.cogame.eventservice.web.messageproxy.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerExtension;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;

import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
/*@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.CLASSPATH,
        ids = {"de.cogame:message-service:+:stubs:8100"})*/
class EventServiceApplicationTests {
    @RegisterExtension
    public StubRunnerExtension stubRunner = new StubRunnerExtension()
            .downloadStub("de.cogame", "message-service", "0.0.1-SNAPSHOT", "stubs")
            .withPort(8100)
            .stubsMode(StubRunnerProperties.StubsMode.LOCAL);


    /*@Autowired
    private MessageController messageController;

    @Test
    void contextLoads() {

        Message mess = messageController.getMessage("1");
        assertThat(mess.getEventId()).isEqualTo("1");
    }*/

    @Test
    public void test() {

    }

}
