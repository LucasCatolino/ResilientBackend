package ar.edu.itba.cleancode.resilientbackend.advicers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.edu.itba.cleancode.resilientbackend.exceptions.CouldNotDeleteTweetException;

@ControllerAdvice
public class CouldNotDeleteTweetAdvice {
    
    @ResponseBody
    @ExceptionHandler(CouldNotDeleteTweetException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String couldNotDeleteTweetHandler(CouldNotDeleteTweetException ex) {
        return ex.getMessage();
    }
}
