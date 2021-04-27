package de.cogame.userservice.initializr;

import de.cogame.userservice.model.Account;
import de.cogame.userservice.model.Occupation;
import de.cogame.userservice.model.Sex;
import de.cogame.userservice.model.User;
import de.cogame.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class Initializr{

    @Autowired
    UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void go(){

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

        userRepository.deleteAll();
        this.userRepository.save(user);
        this.userRepository.save(user);
    }

}