package de.cogame.eventservice.model;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import de.cogame.globalhandler.validation.EnumValidation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Contains information to activities of the event")
public class Activity{

    public enum ActivityArt {
        COMPUTER,
        TABLE,
        ACTIVE

    }

    @EnumValidation(enumClass = ActivityArt.class, message =AttributeDescription.activityArt)
    @ApiModelProperty(notes =  AttributeDescription.activityArt)
    private String activityArt;

    @Size(min= AttributeDescription.activityNameSize, message = AttributeDescription.activityName)
    @ApiModelProperty(notes =  AttributeDescription.activityName)
    private String name;
}

