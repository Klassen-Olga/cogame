package de.cogame.userservice.service;

import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.userservice.model.User;
import de.cogame.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }
    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("User with id " + id + " does not exist");
        });

    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean areEventsInFuture(List<LocalDateTime> eventsDatesTimes) {
        for(LocalDateTime dateTime:eventsDatesTimes){
            if (dateTime.isAfter(LocalDateTime.now())){
                return true;
            }
        }
        return false;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
