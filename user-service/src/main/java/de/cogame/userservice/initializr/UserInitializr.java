package de.cogame.userservice.initializr;

import de.cogame.userservice.model.Account;
import de.cogame.userservice.model.Occupation;
import de.cogame.userservice.model.PlaceOfLiving;
import de.cogame.userservice.model.User;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Class will be used for user-api testing and db initialising, creates a user
 */
public class UserInitializr {

    public static User getUser(String id, String name) {
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

        return new User(id,
                name,
                LocalDate.of(1996, 4, 5),
                "FEMALE",
                "19083209",
                new PlaceOfLiving("Berlin", "Germany"),
                new Occupation("Student", "University of applied science"),
                new Account( name+"@mail.com", "12345678"),
                books, movies, games, music);

    }
}
