package de.cogame.messageservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@ApiModel(description = "Contains a message-resource for a specific event from a specific user")
public class Message {
    @Id
    private String id;

    @NotEmpty
    @ApiModelProperty(notes = "Id of the user who posted the message. Should not be empty")
    private String userId;

    @NotEmpty
    @ApiModelProperty(notes = "Name of the user who posted the message. Should not be empty")
    private String userName;

    @NotEmpty
    @ApiModelProperty(notes = "Id of the event, to which the message is referring")
    private String eventId;

    @ApiModelProperty(notes = "Message itself")
    private String text;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt=LocalDateTime.now();
}
