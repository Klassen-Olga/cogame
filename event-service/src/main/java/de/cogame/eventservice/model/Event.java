package de.cogame.eventservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Event {
    @Id
    private String id;
    @Size(min = 3, message = "Name should have at least 3 characters ")
    private String name;
    private String description;

    @Future
    private LocalDateTime dateTimeOfEvent;
    @Valid
    private Address placeAddress;
    @NotNull
    @Size(min = 1, message="At least 1 activity required")
    private List<@Valid Activity> activities;
    @NotEmpty
    private String creatorUserId;
    // redundancy to prevent calling the user-service
    @Valid
    private Map<@NotBlank String, @NotBlank String> participants;

    //includes the creator
    @Min(value = 2, message = "Participants number of any event should be more than 1 person")
    private int participantsNumber;

    private List<Tool> tools;
    private LocalDateTime createdAt = LocalDateTime.now();

}
