package de.cogame.userservice.web;

import de.cogame.globalhandler.GlobalHandlerApplication;
import de.cogame.globalhandler.exception.UserNotFoundException;
import de.cogame.userservice.model.User;
import de.cogame.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@Log4j2
public class UserController {

    UserRepository userRepository;

    @GetMapping("/")
    public String greeting() {
        return GlobalHandlerApplication.hello();
    }

    @GetMapping("/users")
    public List<User> getUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {

        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()){
            throw new UserNotFoundException("User with id "+id+" does not exist");
        }

        return user.get();

    }

}
