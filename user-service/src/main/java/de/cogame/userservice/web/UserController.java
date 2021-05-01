package de.cogame.userservice.web;


import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.userservice.model.User;
import de.cogame.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@Log4j2
public class UserController {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<User> getUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {

        Optional<User> user = userRepository.findById(id);
        
        if (!user.isPresent()){
            throw new NotFoundException("User with id "+id+" does not exist");
        }

        return user.get();

    }
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        user.getAccount().setPassword(passwordEncoder.encode(user.getAccount().getPassword()));
        User savedUser=userRepository.save(user);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {

        userRepository.deleteById(id);

    }

    @PutMapping("/users/{id}")
    public void updateUser(@RequestBody User user) {

        userRepository.save(user);

    }

}
