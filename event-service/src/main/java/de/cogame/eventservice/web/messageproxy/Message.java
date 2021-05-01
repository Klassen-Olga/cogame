package de.cogame.eventservice.web.messageproxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String id;
    private String userId;
    private String userName;
    private String eventId;
    private String text;
    private LocalDateTime createdAt;
}
