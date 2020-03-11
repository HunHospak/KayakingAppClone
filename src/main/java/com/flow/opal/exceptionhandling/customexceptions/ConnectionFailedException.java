package com.flow.opal.exceptionhandling.customexceptions;

public class ConnectionFailedException extends RuntimeException {

  public ConnectionFailedException(String message) {
    super(message);
  }
}
