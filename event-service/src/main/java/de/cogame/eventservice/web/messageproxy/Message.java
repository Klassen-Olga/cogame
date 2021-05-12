package de.cogame.eventservice.web.messageproxy;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Representation class of the Message class in the message-service module
 * It will be instantiated on the event-service side after an http request to the message-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String id;
    private String userId;
    private String userName;
    private String eventId;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;
}
