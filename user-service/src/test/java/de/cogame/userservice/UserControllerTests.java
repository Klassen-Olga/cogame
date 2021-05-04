package de.cogame.userservice;

import de.cogame.userservice.model.Account;
import de.cogame.userservice.model.Occupation;
import de.cogame.userservice.model.PlaceOfLiving;
import de.cogame.userservice.model.User;
import de.cogame.userservice.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
@RunWith(SpringRunner.class)//no ApplicationContext will be loaded
public class UserControllerTests {
    @MockBean(name="userRepository")
    private UserRepository userRepository;


    @Before
    public void before() throws IOException {

        List<String> books = new LinkedList<>();

        books.add("book1");
        books.add("book2");
        books.add("book3");

        List<String> movies = new LinkedList<>();

        movies.add("movie1");
        movies.add("movie2");
        movies.add("movie3");

        List<String> games = new LinkedList<>();

        games.add("game1");
        games.add("game2");


        List<String> music = new LinkedList<>();

        music.add("music1");
        music.add("music2");
        music.add("music3");

        User user = new User("1","Albert", LocalDate.of(1996, 4, 5), "FEMALE",
                new PlaceOfLiving("Berlin", "Germany"),
                new Occupation(" ", " "), "19083209",
                books, movies, games, music, new Account("mail@mail.com", "12345678"));

        userRepository.deleteAll();
        this.userRepository.save(user);
    }
    @Test
    public void getAllUsersReturnsFalse() throws IOException {

        List<User> users=userRepository.findAll();
        assertThat(userRepository.findAll().isEmpty()).isEqualTo(false);




    }

    @Test
    public void getUserReturnsId1(){
        List<User> users=userRepository.findAll();
        assertThat(userRepository.findAll().get(0).getId()).isEqualTo("1");
    }
}