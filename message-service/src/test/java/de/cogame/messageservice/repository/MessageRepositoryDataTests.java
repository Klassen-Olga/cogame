package de.cogame.messageservice.repository;

import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.messageservice.initializr.MessageInitializr;
import de.cogame.messageservice.model.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
/*
 * Test class with the embedded mongoDb
 * with injected MessageRepository Bean from the spring context
 * */
@RunWith(SpringRunner.class)
@DataMongoTest
public class MessageRepositoryDataTests {

    @Autowired
    private MessageRepository messageRepository;

    @Before
    public void init(){
        List<Message> messages = MessageInitializr.getMessages();
        messageRepository.save(messages.get(0));
        messageRepository.save(messages.get(1));
        messageRepository.save(messages.get(2));
    }
    @Test
    public void shouldContain3MessagesForEventId1(){
        assertThat(this.messageRepository.findByEventId("1").size()).isEqualTo(3);
    }

    @Test
    public void shouldContainMessageWithId1UserId1EventId1(){
        Optional<Message> optionalMessage = messageRepository.findById("1");
        if (optionalMessage.isEmpty()){
            throw new NotFoundException("Message repository is empty");
        }
        assertThat(optionalMessage.get().getEventId()).isEqualTo("1");
        assertThat(optionalMessage.get().getUserId()).isEqualTo("1");
        assertThat(optionalMessage.get().getUserName()).isEqualTo("Name1");
        assertThat(optionalMessage.get().getText()).isEqualTo("A great place");
        assertThat(optionalMessage.get().getCreatedAt()).isEqualTo(LocalDateTime.of(2021, 05, 10, 13, 8));
    }
}
