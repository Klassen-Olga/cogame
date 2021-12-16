package de.cogame.messageservice.web.eventproxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Representation  of the Event class in the event-service module
 * It will be instantiated on the message-service side after an http request to the event-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    String id;

}
