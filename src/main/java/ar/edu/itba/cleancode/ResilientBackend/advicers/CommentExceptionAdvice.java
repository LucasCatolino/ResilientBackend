package ar.edu.itba.cleancode.resilientbackend.advicers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.edu.itba.cleancode.resilientbackend.exceptions.CommentException;

@ControllerAdvice
public class CommentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(CommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String commentNotFoundHandler(CommentException ex) {
        return ex.getMessage();
    }
    
}
