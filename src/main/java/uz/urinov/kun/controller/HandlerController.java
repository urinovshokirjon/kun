package uz.urinov.kun.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.urinov.kun.exp.AppBadException;
import uz.urinov.kun.exp.AppForbiddenException;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<String> handler(AppBadException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AppForbiddenException.class)
    public ResponseEntity<String> handler(AppForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
