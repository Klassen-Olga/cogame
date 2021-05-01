package de.cogame.eventservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Size(min=5, message="Street should have at least 5 characters ")
    private String streetAndHomeNumber;
    @Size(min=3, message="City should have at least 3 characters ")
    private String city;
    @Size(min=4, message="Address should have at least 4 characters ")
    private String country;
}
