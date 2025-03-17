package com.narlock.kaizenprofileapi.model.error;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}
