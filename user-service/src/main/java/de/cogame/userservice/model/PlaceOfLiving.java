package de.cogame.userservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Contains user's address")
public class PlaceOfLiving {

    @Size(min = AttributeDescription.citySize, message = AttributeDescription.city)
    @ApiModelProperty(notes = AttributeDescription.city)
    private String city;

    @Size(min = AttributeDescription.countrySize, message = AttributeDescription.country)
    @ApiModelProperty(notes = AttributeDescription.country)
    private String country;
}
