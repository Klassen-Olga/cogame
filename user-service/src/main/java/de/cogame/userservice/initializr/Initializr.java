package de.cogame.userservice.initializr;

import de.cogame.userservice.model.User;
import de.cogame.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
/**
 * Inserts a user seed into database userdb on the start of the server
 *
 */
@Component
@RequiredArgsConstructor
public class Initializr {

    @Autowired
    UserRepository userRepository;

    /**
     * removes all created users and inserts one new user with id 1
     */
    @EventListener(ApplicationReadyEvent.class)
    public void go() {

        User user = UserInitializr.getUser("1", "Albert");
        User user1 = UserInitializr.getUser("2", "Cup");

        userRepository.deleteAll();
        this.userRepository.save(user);
        this.userRepository.save(user1);
    }


}