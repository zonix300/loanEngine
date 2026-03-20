package com.example.loanEngine.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ExceptionHandlingController
 */
@Controller
public class ExceptionHandlingController {

  @ExceptionHandler(BorrowerNotFoundException.class)
  public ResponseEntity<?> handleNotFound(BorrowerNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
  }

}
