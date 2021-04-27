package de.cogame.userservice.web;

import de.cogame.userservice.model.Account;
import de.cogame.userservice.model.Occupation;
import de.cogame.userservice.model.Sex;
import de.cogame.userservice.model.User;
import de.cogame.userservice.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@RestController
@Log4j2
public class UserController {

    @GetMapping("/")
    public String greeting(){
        return "Hello from user service!";
    }

}
