package de.cogame.userservice.web;


import de.cogame.globalhandler.exception.EventConstraintViolation;
import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.globalhandler.exception.UniqueKeyViolation;
import de.cogame.userservice.model.User;
import de.cogame.userservice.service.UserService;
import de.cogame.userservice.web.eventproxy.EventServiceProxy;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
/**
 * Restapi controller for user-service module
 * Manages get, post, put and delete methods
 */
@AllArgsConstructor
@RestController
@Log4j2
public class UserController {

    UserService userService;
    PasswordEncoder passwordEncoder;
    EventServiceProxy eventServiceProxy;



    @GetMapping("/users")
    public List<User> getUsers() {

        return userService.getAll();
    }


    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {


        return userService.getUser(id);

    }


    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

        user.getAccount().setPassword(passwordEncoder.encode(user.getAccount().getPassword()));

        try {
            User savedUser = userService.save(user);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedUser.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RuntimeException exception) {
            // if exception is of type E11000 throw high level exception
            if (exception.getMessage().contains("E11000 duplicate key error")) {
                throw new UniqueKeyViolation(exception.getMessage());
            }
            throw exception;
        }


    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {

        // check if user exists
        User user = userService.getUser(id);
        //check if user has event created
        List<LocalDateTime> eventsDatesTimes=eventServiceProxy.getUsersEventDateTimes(id);
        if (userService.areEventsInFuture(eventsDatesTimes)){
            throw new EventConstraintViolation("An user with active created events can not be deleted");
        }
        //delete user from all events
        eventServiceProxy.deleteUserFromEvents(id);
        // delete user
        userService.delete(user);
    }

}
