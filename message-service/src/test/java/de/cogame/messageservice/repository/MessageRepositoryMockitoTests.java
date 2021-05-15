package de.cogame.messageservice.repository;

import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.messageservice.initializr.MessageInitializr;
import de.cogame.messageservice.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/*
 * Test class with event repository as mock object
 * */
@RunWith(SpringRunner.class)
public class MessageRepositoryMockitoTests {

    @MockBean
    private MessageRepository messageRepository;

    @Test
    public void shouldContain3MessagesForEventId1(){
        List<Message> messages = MessageInitializr.getMessages();
        given(this.messageRepository.findById("1")).willReturn(Optional.ofNullable(messages.get(0)));
        Optional<Message> optionalMessage = this.messageRepository.findById("1");

        assertThat(optionalMessage.get().getEventId()).isEqualTo("1");
        assertThat(optionalMessage.get().getUserId()).isEqualTo("1");
        assertThat(optionalMessage.get().getUserName()).isEqualTo("Name1");
        assertThat(optionalMessage.get().getText()).isEqualTo("A great place");
        assertThat(optionalMessage.get().getCreatedAt()).isEqualTo(LocalDateTime.of(2021, 05, 10, 13, 8));
    }


}
