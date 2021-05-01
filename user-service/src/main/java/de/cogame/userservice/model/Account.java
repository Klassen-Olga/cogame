package de.cogame.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    //@Email
   // @NotBlank
    private String email;
   // @Size(min = 6, message = "Password should be longer than 5 characters")
    private String password;


}
