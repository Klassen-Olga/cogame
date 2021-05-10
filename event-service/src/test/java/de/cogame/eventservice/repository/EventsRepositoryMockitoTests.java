package de.cogame.eventservice.repository;

import de.cogame.eventservice.initializr.EventInitializr;
import de.cogame.eventservice.model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class EventsRepositoryMockitoTests {

    @MockBean
    private EventRepository eventRepository;

    @Test
    public void getEventById1ReturnsFriendsEvening(){
        given(eventRepository.findById("1")).willReturn(Optional.of(EventInitializr.getEvent("1")));

        Optional<Event> event = eventRepository.findById("1");
        assertThat(event.isPresent()).isEqualTo(true);
        Event event1=event.get();
        assertThat(event1.getName()).isEqualTo("Friends evening");
        assertThat(event1.getDescription()).isEqualTo("A party where we are going to play both monopoly and twister");
        assertThat(event1.getParticipantsNumber()).isEqualTo(4);
        assertThat(event1.getDateTimeOfEvent()).isEqualTo(LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(12, 0)));
        assertThat(event1.getTools().get(0).getToolName()).isEqualTo("Monopoly game");

    }
}
