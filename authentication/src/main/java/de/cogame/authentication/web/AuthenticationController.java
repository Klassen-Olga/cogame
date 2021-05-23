package de.cogame.authentication.web;

import de.cogame.authentication.db.Authentication;
import de.cogame.authentication.db.AuthenticationRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
public class AuthenticationController {

    @Value("${user.password}")
    private String userPassword;
    @Value("${event.password}")
    private String eventPassword;
    @Value("${message.password}")
    private String messagePassword;

    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public void auth(@RequestHeader("X-Script-Name") String serviceName,
                            @RequestHeader("key") String key) {


        String serviceHash = getServiceHash(serviceName);
        log.info("KEY"+key);
        if (!passwordEncoder.matches(key, serviceHash)){
            throw new UnauthorizedException("Request was unauthorised");

        }
    }



    private String getServiceHash(String serviceName) {
        List<Authentication> data = authenticationRepository.findByServiceName(serviceName);
        if (data.isEmpty()) {
            throw new RuntimeException("Service can not be authenticated. " +
                    "Authentication data does not exist");
        }
        return data.get(0).getServiceHash();
    }

}
