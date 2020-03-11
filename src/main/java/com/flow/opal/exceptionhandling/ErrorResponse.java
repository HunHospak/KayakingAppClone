package com.flow.opal.exceptionhandling;

import java.util.Date;
import org.springframework.http.HttpStatus;

public class ErrorResponse {
  private HttpStatus status;
  private String message;
  private String path;
  private Date timeStamp;

  public ErrorResponse() {
    this.timeStamp = new Date();
  }

  public ErrorResponse(HttpStatus status, String message, String path) {
    this.status = status;

    this.message = message;
    this.path = path;
    this.timeStamp = new Date();
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }
}
