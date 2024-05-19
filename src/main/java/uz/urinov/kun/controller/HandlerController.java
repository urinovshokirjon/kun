package uz.urinov.kun.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.urinov.kun.exp.AppBadException;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<String> handler(AppBadException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
