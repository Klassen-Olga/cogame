package de.cogame.eventservice.model;

import de.cogame.globalhandler.validation.EnumValidation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * Represents activity unit of the event
 * important features: art of the activity and activity name
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Contains information about the activities of the event")
public class Activity {

    public enum ActivityArt {
        COMPUTER,
        TABLE,
        ACTIVE

    }

    @EnumValidation(enumClass = ActivityArt.class, message = AttributeDescription.activityArt)
    @ApiModelProperty(notes = AttributeDescription.activityArt)
    private String activityArt;

    @Size(min = AttributeDescription.activityNameSize, message = AttributeDescription.activityName)
    @ApiModelProperty(notes = AttributeDescription.activityName)
    private String name;
}

