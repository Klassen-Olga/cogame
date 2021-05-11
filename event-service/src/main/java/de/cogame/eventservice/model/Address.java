package de.cogame.eventservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Contains address info of the event")
public class Address {

    @Size(min=AttributeDescription.streetSize, message=AttributeDescription.street)
    @ApiModelProperty(notes =  AttributeDescription.street)
    private String streetAndHomeNumber;

    @Size(min=AttributeDescription.citySize, message=AttributeDescription.city)
    @ApiModelProperty(notes =  AttributeDescription.city)
    private String city;

    @Size(min=AttributeDescription.countrySize, message=AttributeDescription.country)
    @ApiModelProperty(notes =  AttributeDescription.country)
    private String country;
}
