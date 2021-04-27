package de.cogame.userservice.model;

import de.cogame.userservice.repository.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Occupation {

    private  String occupationName;
    private  String placeOfOccupation;

}
