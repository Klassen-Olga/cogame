package de.cogame.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Occupation {

  //  @Size(min = 5, message = "Enter a valid occupation name e.g. 'Student', 'Worker'")
    private  String occupationName;
 //   @Size(min = 4, message = "Enter a valid place of occupation e.g. 'University of applied science'")
    private  String placeOfOccupation;

}
