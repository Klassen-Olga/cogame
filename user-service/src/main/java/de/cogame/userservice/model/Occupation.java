package de.cogame.userservice.model;

import de.cogame.userservice.repository.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Occupation {

    @Size(min = 5, message = "Enter a valid occupation name e.g. 'Student', 'Worker'")
    private  String occupationName;
    @Size(min = 4, message = "Enter a valid place of occupation e.g. 'University of applied science'")
    private  String placeOfOccupation;

}
