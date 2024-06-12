package az.evilcastle.crossingtherubicon.controller;

import az.evilcastle.crossingtherubicon.exceptions.LobbyIsFullException;
import az.evilcastle.crossingtherubicon.exceptions.LobbyIsNotFound;
import az.evilcastle.crossingtherubicon.model.dto.ErrorResponse;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleError(Exception ex) {
        log.error("Unexpected error", ex);
        return new ErrorResponse("unexpected.error", HttpStatus.INTERNAL_SERVER_ERROR.name());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LobbyIsNotFound.class)
    public ErrorResponse handleError(LobbyIsNotFound exception){
        log.error("LOBBY IS NOT FOUND",exception);
        return new ErrorResponse("LOBBY IS NOT FOUND",HttpStatus.BAD_REQUEST.name());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LobbyIsFullException.class)
    public ErrorResponse handleError(LobbyIsFullException exception){
        log.error("LOBBY IS ALREADY FULL",exception);
        return new ErrorResponse("LOBBY IS ALREADY FULL",HttpStatus.BAD_REQUEST.name());
    }
}
