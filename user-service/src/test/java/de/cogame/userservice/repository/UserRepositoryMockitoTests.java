package de.cogame.userservice.repository;

import de.cogame.userservice.initializr.UserInitializr;
import de.cogame.userservice.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class UserRepositoryMockitoTests {

    @MockBean
    private UserRepository userRepository;


    @Test
    public void getUserById1ReturnsAlbert() {

        User user = UserInitializr.getUser("1");

        given(this.userRepository.findById("1")).willReturn(java.util.Optional.of(user));

        Optional<User> user1 = userRepository.findById("1");
        if (user1.isPresent()) {
            assertThat(user1.get().getName()).isEqualTo("Albert");
            assertThat(user1.get().getAccount().getEmail()).isEqualTo("mail@mail.com");
            assertThat(user1.get().getName()).isEqualTo("Albert");
            assertThat(user1.get().getDateOdBirth()).isEqualTo(LocalDate.of(1996, 4, 5));
        }
    }

}
