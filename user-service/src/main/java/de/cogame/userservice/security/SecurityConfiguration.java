package de.cogame.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Overrides default security configurations of spring-boot-starter-security
 */
@Configuration
public class SecurityConfiguration  {

    /**
     * @return password encoder bean, which provides encryption and decryption of user's password
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
