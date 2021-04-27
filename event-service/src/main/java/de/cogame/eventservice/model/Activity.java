package de.cogame.eventservice.model;

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
    private ActivityArt activityArt;
    private String name;
}

