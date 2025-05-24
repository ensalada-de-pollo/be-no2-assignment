package com.be_no2_assignment.lv3_6.common;

import com.be_no2_assignment.lv3_6.common.exception.BadInputException;
import com.be_no2_assignment.lv3_6.common.exception.DataNotFoundException;
import com.be_no2_assignment.lv3_6.common.exception.PasswordMismatchException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<ErrorResult> handleDataNotFoundException(DataNotFoundException e) {
    return new ResponseEntity<>(new ErrorResult(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(PasswordMismatchException.class)
  public ResponseEntity<ErrorResult> handlePasswordMismatchException(PasswordMismatchException e) {
    return new ResponseEntity<>(new ErrorResult(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadInputException.class)
  public ResponseEntity<ErrorResult> handleBadInputException(BadInputException e) {
    return new ResponseEntity<>(new ErrorResult(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  // lv6
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    return new ResponseEntity<>(new ErrorResult(HttpStatus.BAD_REQUEST,
        e.getBindingResult().getFieldErrors().stream()
        .map(MessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining())), HttpStatus.BAD_REQUEST);
  }
}
