package org.example.response.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.exceptions.EpicNotFoundException;
import org.example.dao.exceptions.SubTaskNotFoundException;
import org.example.dao.exceptions.TaskNotFoundException;
import org.example.response.ResponseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class TaskExceptionHandler {
    @ExceptionHandler({TaskNotFoundException.class, EpicNotFoundException.class, SubTaskNotFoundException.class})
    public ResponseEntity<ResponseErrorMessage> catchEntityNotFoundException() {
        ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage();
        responseErrorMessage.setMessage("Entity with this id not found");
        log.error(responseErrorMessage.getMessage());
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND);
    }
}
