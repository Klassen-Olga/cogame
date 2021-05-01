package de.cogame.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Address;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {

    @Id
    private String id;
  //  @Size(min=2, message="Name should have at least 2 characters")
    private String name;
  //  @Past
    private LocalDate dateOdBirth;
    private Sex sex;
   // @Valid
    private PlaceOfLiving placeOfLiving;
  //  @Valid
    private Occupation occupation;
  //  @Size(min=4, message="Enter a valid phone number")
    private String phoneNumber;


    private List<String> favouriteBooks;
    private List<String> favouriteMovies;
    private List<String> favouriteGames;
    private List<String> favouriteMusic;
    //@Valid
    private Account account;

}
