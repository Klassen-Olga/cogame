package de.cogame.eventservice.model;

import de.cogame.globalhandler.validation.EnumValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity{
    public enum ActivityArt {
        COMPUTER,
        TABLE,
        ACTIVE

    }
    @EnumValidation(enumClass = ActivityArt.class, message = "Allowed values: TABLE, ACTIVE, COMPUTER")
    private String activityArt;
    private String name;
}

