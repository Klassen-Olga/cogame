package de.cogame.eventservice.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Contains information to a tool, that is required for a certain event e.g. Ball, Beer")
public class Tool {

    private String toolName;
    private boolean alreadyExists;
}
