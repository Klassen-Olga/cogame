package de.cogame.messageservice.web.userproxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representation  of the User class in the message-service module
 * It will be instantiated on the message-service side after an http request to the user-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String id;
}
