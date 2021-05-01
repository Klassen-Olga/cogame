package de.cogame.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOfLiving {
  //  @Size(min = 5, message = "Enter a valid city name")
    private String city;
  //  @Size(min = 5, message = "Enter a valid country name")
    private String country;
}
