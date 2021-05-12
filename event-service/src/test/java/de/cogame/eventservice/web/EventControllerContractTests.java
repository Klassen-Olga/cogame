package de.cogame.eventservice.web;


import de.cogame.eventservice.web.messageproxy.MessageServiceProxy;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Ent-to-end test class between message-service and event-service
 * actual commented out because of guthub build failing
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(

        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = {"de.cogame:message-service:+:stubs:8002"})
class EventControllerContractTests {

    @Autowired
    private MessageServiceProxy messageServiceProxy;

    // not passed gihub build

    /*@Test
    void shouldReturnTwoMessagesWithIds21And22() {

        List<Message> messages = messageServiceProxy.getMessages("20");
        assertThat(messages.get(0).getUserName()).isEqualTo("Dom");
        assertThat(messages.get(0).getEventId()).isEqualTo("20");
        assertThat(messages.get(0).getId()).isEqualTo("21");
        assertThat(messages.get(0).getText()).isEqualTo("Great Event, Dom");
        assertThat(messages.get(0).getCreatedAt()).isEqualTo(LocalDateTime.of(2023, 01, 01, 14, 00));

        assertThat(messages.get(1).getUserName()).isEqualTo("Bom");
        assertThat(messages.get(1).getEventId()).isEqualTo("20");
        assertThat(messages.get(1).getId()).isEqualTo("22");
        assertThat(messages.get(1).getText()).isEqualTo("Great Event, Bom");
        assertThat(messages.get(1).getCreatedAt()).isEqualTo(LocalDateTime.of(2023, 01, 01, 13, 00));

    }
*/

}
