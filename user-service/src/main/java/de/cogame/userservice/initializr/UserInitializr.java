package de.cogame.userservice.initializr;

import de.cogame.userservice.model.Account;
import de.cogame.userservice.model.Occupation;
import de.cogame.userservice.model.PlaceOfLiving;
import de.cogame.userservice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


public class UserInitializr {

    public static User getUser(String id){
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

        return new User(id,"Albert", LocalDate.of(1996, 4, 5), "FEMALE",
                new PlaceOfLiving("Berlin", "Germany"),
                new Occupation(" ", " "), "19083209",
                books, movies, games, music, new Account("mail@mail.com", "12345678"));

    }
}