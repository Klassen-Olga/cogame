package de.cogame.userservice.model;

import de.cogame.globalhandler.validation.EnumValidation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Address;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@ApiModel(description = "Contains user details for the cogame project")
public class User {

    @Id
    private String id;

    @Size(min = AttributeDescription.userNameSize, message = AttributeDescription.userName)
    @ApiModelProperty(notes =  AttributeDescription.userName)
    private String name;

    @Past(message = AttributeDescription.dateOdBirth)
    @ApiModelProperty(notes = AttributeDescription.dateOdBirth)
    private LocalDate dateOdBirth;

    @EnumValidation(enumClass = Sex.class, message = AttributeDescription.sex)
    @ApiModelProperty(notes = AttributeDescription.sex)
    private String sex;

    @ApiModelProperty(notes=AttributeDescription.phoneNumber)
    @Size(min = AttributeDescription.phoneNumberSize, message = AttributeDescription.phoneNumber)
    private String phoneNumber;

    @Valid
    private PlaceOfLiving placeOfLiving;
    @Valid
    private Occupation occupation;
    @Valid
    private Account account;


    @ApiModelProperty(notes=AttributeDescription.preferencesList)
    private List<String> favouriteBooks;
    @ApiModelProperty(notes=AttributeDescription.preferencesList)
    private List<String> favouriteMovies;
    @ApiModelProperty(notes=AttributeDescription.preferencesList)
    private List<String> favouriteGames;
    @ApiModelProperty(notes=AttributeDescription.preferencesList)
    private List<String> favouriteMusic;

}
