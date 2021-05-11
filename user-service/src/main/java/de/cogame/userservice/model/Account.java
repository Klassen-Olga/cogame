package de.cogame.userservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Contains authentication data: login and password")
public class Account {

    @Email(message = AttributeDescription.email)
    @NotBlank
    @ApiModelProperty(notes = AttributeDescription.email)
    private String email;

    @Size(min = AttributeDescription.passwordSize, message = AttributeDescription.password)
    @ApiModelProperty(notes = AttributeDescription.password)
    private String password;


}
