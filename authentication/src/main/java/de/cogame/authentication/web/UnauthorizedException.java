package de.cogame.authentication.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException{
    private String message;

    public UnauthorizedException(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
