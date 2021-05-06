package de.cogame.globalhandler.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> notFoundException
            (NotFoundException ex, WebRequest webRequest){
        String m= ex.getMessage();

        ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDate.now(), ex.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> exception
            (NotFoundException ex, WebRequest webRequest){
        String m= ex.getMessage();

        ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDate.now(), ex.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex,
             HttpHeaders headers,
             HttpStatus status,
             WebRequest request){

        ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDate.now(), "Validation failed",
                ex.getBindingResult().toString());
        return  new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable
            (HttpMessageNotReadableException ex,
             HttpHeaders headers,
             HttpStatus status,
             WebRequest request){

        ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDate.now(), "Validation failed",
               "Required request body is missing");
        return  new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);    }


}