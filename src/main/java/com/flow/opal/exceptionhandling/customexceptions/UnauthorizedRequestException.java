package com.flow.opal.exceptionhandling.customexceptions;

public class UnauthorizedRequestException extends RuntimeException {

  public UnauthorizedRequestException(String message) {
    super(message);
  }
}
