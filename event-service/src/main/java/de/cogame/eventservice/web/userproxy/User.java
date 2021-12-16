package de.cogame.eventservice.web.userproxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representation of the User class in the user-service module
 * It will be instantiated on the event-service side after an http request to the user-service
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String id;
}
