package de.cogame.globalhandler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NumberOfParticipantsReached extends RuntimeException {
    private String message;

    public NumberOfParticipantsReached(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
