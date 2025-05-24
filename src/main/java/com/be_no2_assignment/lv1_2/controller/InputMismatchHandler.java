package com.be_no2_assignment.lv1_2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.InputMismatchException;

@RestControllerAdvice
@Slf4j
public class InputMismatchHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InputMismatchException.class)
  public ResponseEntity<String> inputMismatchHandler(InputMismatchException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
