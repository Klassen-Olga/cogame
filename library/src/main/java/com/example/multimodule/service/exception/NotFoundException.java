package com.example.multimodule.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    private  String message;

    public NotFoundException(String s) {
        this.message=s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
