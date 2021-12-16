package de.cogame.userservice.repository;

import de.cogame.userservice.initializr.UserInitializr;
import de.cogame.userservice.model.User;
import de.cogame.userservice.web.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
/*
*
* Test class with embedded mongoDb
* with injected UserRepository Bean from the context
* */
@DataMongoTest
@RunWith(SpringRunner.class)
public class UserRepositoryDataTests {

    @Autowired
    UserRepository userRepository;

    @Before
    public void before(){

        userRepository.deleteAll();
        this.userRepository.save(UserInitializr.getUser("1", "albert"));


    }

    @Test
    public void usersRepositoryIsNotEmpty() throws IOException {

        assertThat(userRepository.findAll().isEmpty()).isEqualTo(false);
    }
    @Test
    public void getUserReturnsAlbert() throws IOException {

        Optional<User> user = userRepository.findById("1");
        if (user.isPresent()){
            assertThat(user.get().getName()).isEqualTo("albert");
            assertThat(user.get().getDateOdBirth()).isEqualTo(LocalDate.of(1996, 04,05));
            assertThat(user.get().getAccount().getEmail()).isEqualTo("albert@mail.com");
            assertThat(user.get().getAccount().getPassword()).isEqualTo("12345678");
            assertThat(user.get().getPlaceOfLiving().getCity()).isEqualTo("Berlin");
        }


    }
}

