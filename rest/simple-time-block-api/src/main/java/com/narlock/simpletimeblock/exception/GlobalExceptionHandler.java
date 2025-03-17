package com.narlock.simpletimeblock.exception;

import com.narlock.simpletimeblock.exception.response.ErrorResponse;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  final String BAD_REQUEST_STATUS = "400 Bad Request";
  final String NOT_FOUND_STATUS = "404 Not Found";
  final String INTERNAL_SERVER_ERROR_STATUS = "500 Internal Server Error";

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchExceptionHandler(
      final MethodArgumentTypeMismatchException e) {
    log.error(
        "MethodArgumentTypeMismatchException caught in controller advice: {}", e.getMessage());
    return createErrorResponse(
        ErrorResponse.builder()
            .status(BAD_REQUEST_STATUS)
            .message(
                "Parameter " + e.getPropertyName() + " failed to be parsed, please check format")
            .timestamp(LocalDateTime.now().toString())
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> dataIntegrityViolationExceptionHandler(
      final DataIntegrityViolationException e) {
    log.error("DataIntegrityViolationException caught in controller advice: {}", e.getMessage());
    if (e.getRootCause() != null) {
      return createErrorResponse(
          ErrorResponse.builder()
              .status(BAD_REQUEST_STATUS)
              .message(e.getRootCause().getMessage())
              .timestamp(LocalDateTime.now().toString())
              .build(),
          HttpStatus.BAD_REQUEST);
    } else {
      return createErrorResponse(
          ErrorResponse.builder()
              .status(BAD_REQUEST_STATUS)
              .message("An unexpected request was received, please check your request body")
              .timestamp(LocalDateTime.now().toString())
              .build(),
          HttpStatus.BAD_REQUEST);
    }
  }

  @ExceptionHandler(CalendarEventNotFoundException.class)
  public ResponseEntity<ErrorResponse> calendarEventNotFoundExceptionHandler(
      final CalendarEventNotFoundException e) {
    log.error(
        "CalendarEventNotFoundException caught in controller advice: {} id: {}",
        e.getMessage(),
        e.getCalendarEventId());
    return createErrorResponse(
        ErrorResponse.builder()
            .status(NOT_FOUND_STATUS)
            .message("Calendar Event was not found for id " + e.getCalendarEventId())
            .detail("Please enter a calendar event id that is valid")
            .timestamp(LocalDateTime.now().toString())
            .build(),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NoCalendarEventOnDayException.class)
  public ResponseEntity<ErrorResponse> noCalendarEventOnDayExceptionHandler(
      final NoCalendarEventOnDayException e) {
    log.error(
        "NoCalendarEventOnDayException caught in controller advice: {} date: {}",
        e.getMessage(),
        e.getDate());
    return createErrorResponse(
        ErrorResponse.builder()
            .status(NOT_FOUND_STATUS)
            .message("No calendar events on " + e.getDate())
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
