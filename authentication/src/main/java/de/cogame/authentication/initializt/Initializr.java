package de.cogame.authentication.initializt;

import de.cogame.authentication.db.Authentication;
import de.cogame.authentication.db.AuthenticationRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class Initializr {

    AuthenticationRepository authenticationRepository;
    private PasswordEncoder passwordEncoder;
    /**
     * removes all created messages and inserts tree messages
     */
    @EventListener(ApplicationReadyEvent.class)
    public void go() {
        authenticationRepository.deleteAll();
        authenticationRepository.save(new Authentication("", "user",
                passwordEncoder.encode("123")));
        authenticationRepository.save(new Authentication("", "event",
                passwordEncoder.encode("456")));
        authenticationRepository.save(new Authentication("", "message",
                passwordEncoder.encode("789")));

    }
}