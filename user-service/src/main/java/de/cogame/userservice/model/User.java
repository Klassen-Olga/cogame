package de.cogame.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {

    @Id
    private Long id;
    private LocalDate dateOdBirth;
    private Sex sex;
    private Occupation occupation;
    private String phoneNumber;

    private List<String> favouriteBooks;
    private List<String> favouriteMovies;
    private List<String> favouriteGames;
    private List<String> favouriteMusic;
    private Account account;

}
