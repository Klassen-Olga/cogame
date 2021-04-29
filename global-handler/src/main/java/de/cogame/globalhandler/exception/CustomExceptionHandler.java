package de.cogame.globalhandler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice(basePackages = "de.cogame.userservice")
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> userNotFoundException
            (UserNotFoundException ex, WebRequest webRequest){
        String m= ex.getMessage();

        ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDate.now(), ex.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
