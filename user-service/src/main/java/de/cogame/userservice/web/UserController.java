package de.cogame.userservice.web;

import de.cogame.userservice.model.Account;
import de.cogame.userservice.model.Occupation;
import de.cogame.userservice.model.Sex;
import de.cogame.userservice.model.User;
import de.cogame.userservice.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@RestController
@Log4j2
public class UserController {

    @GetMapping("/")
    public String greeting(){
        go();
        return "Hello from user service!";
    }

    @GetMapping("/insert-user")
    public User insertUser(){

        return go();
    }
    @Autowired
    UserRepository userRepository;

    public User go(){

        List<String> books=new LinkedList<>();

        books.add("book1");
        books.add("book2");
        books.add("book3");

        List<String> movies=new LinkedList<>();

        movies.add("movie1");
        movies.add("movie2");
        movies.add("movie3");

        List<String> games=new LinkedList<>();

        games.add("game1");
        games.add("game2");


        List<String> music=new LinkedList<>();

        music.add("music1");
        music.add("music2");
        music.add("music3");

        User user=new User("", LocalDate.of(1996, 4, 5), Sex.FEMALE,
                new Occupation(" ", " "), "19083209",
                books, movies,games, music, new Account("mail@mail.com", "12345678"));


        user.setId(null);
        this.userRepository.deleteAll();
        User user1= this.userRepository.save(user);
        log.info(user1.getPhoneNumber());
        return user1;
    }
}
