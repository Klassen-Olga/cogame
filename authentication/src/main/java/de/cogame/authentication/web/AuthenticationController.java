package de.cogame.authentication.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AuthenticationController {


    @GetMapping("/")
    public String greeting1(@RequestHeader("key") String key) {

        if (!key.equals("123")){
            throw new UnauthorizedException("Request was unauthorised");
        }
        return "ggg";
    }

}
