package de.cogame.messageservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Message {
    @Id
    private String id;
    private String userId;
    private String userName;
    private String eventId;
    private String text;
    private LocalDateTime createdAt=LocalDateTime.now();
}
