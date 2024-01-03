package ar.edu.itba.cleancode.resilientbackend.advicers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.edu.itba.cleancode.resilientbackend.exceptions.ReactionException;

@ControllerAdvice
public class ReactionExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ReactionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String appUserNotFoundHandler(ReactionException ex) {
        return ex.getMessage();
    }
    
}
