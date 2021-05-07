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
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
@RunWith(SpringRunner.class)
public class UserRepositoryDataTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserController userController;


    @Before
    public void before() throws IOException {

        userRepository.deleteAll();
        this.userRepository.save(UserInitializr.getUser("1"));


    }

    @Test
    public void getAllUsersReturnsFalse() throws IOException {

        //List<User> users = userRepository.findAll();
        //assertThat(userRepository.findAll().isEmpty()).isEqualTo(false);

        User user=  UserInitializr.getUser("1");
        user.setName("");
        userController.createUser(user);
    }
}

