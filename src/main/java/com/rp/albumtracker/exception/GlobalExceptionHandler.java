package com.rp.albumtracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleResourceNotFoundException(ResourceNotFoundException ex) {
    return new ErrorMessage("Resource not found: " + ex.getMessage());
  }

  @ExceptionHandler(ResourceExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorMessage handleResourceExistsException(ResourceExistsException ex) {
    return new ErrorMessage("Resource already exists: " + ex.getMessage());
  }

}
