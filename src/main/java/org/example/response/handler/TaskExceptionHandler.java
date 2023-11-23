package org.example.response.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.exceptions.EpicNotFoundException;
import org.example.dao.exceptions.SubTaskNotFoundException;
import org.example.dao.exceptions.TaskNotFoundException;
import org.example.response.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class TaskExceptionHandler {
    @ExceptionHandler({TaskNotFoundException.class, EpicNotFoundException.class, SubTaskNotFoundException.class})
    public ResponseEntity<ErrorMessageResponse> catchEntityNotFoundException() {
        ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse();
        errorMessageResponse.setMessage("Entity with this id not found");
        log.error(errorMessageResponse.getMessage());
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.NOT_FOUND);
    }
}
