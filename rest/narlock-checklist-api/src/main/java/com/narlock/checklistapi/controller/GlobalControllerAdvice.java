package com.narlock.checklistapi.controller;

import com.narlock.checklistapi.model.error.ErrorResponse;
import com.narlock.checklistapi.model.error.NotFoundException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {
  final String NOT_FOUND_STATUS = "404 Not Found";
  final String INTERNAL_SERVER_ERROR_STATUS = "500 Internal Server Error";

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException e) {
    return createErrorResponse(
        ErrorResponse.builder()
            .status(NOT_FOUND_STATUS)
            .message(e.getMessage())
            .timestamp(LocalDateTime.now().toString())
            .build(),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> catchAllExceptionHandler(final Exception e) {
    log.error("Exception caught in controller advice: {}", e);
    return createErrorResponse(
        ErrorResponse.builder()
            .status(INTERNAL_SERVER_ERROR_STATUS)
            .message(e.getMessage())
            .timestamp(LocalDateTime.now().toString())
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponse> createErrorResponse(
      ErrorResponse errorResponse, HttpStatus status) {
    return new ResponseEntity<>(errorResponse, status);
  }
}
