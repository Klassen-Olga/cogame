package de.cogame.eventservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Event {
    @Id
    private String id;
    private String name;
    private String description;

    private LocalDateTime dateTimeOfEvent;
    private Address placeAddress;
    private List<Activity> activities;

    private String creatorUserId;
    private List<String> participantsId;
    private int participantsNumber;

    private List<Tool> tools;
    private LocalDateTime createdAt=LocalDateTime.now();

}
