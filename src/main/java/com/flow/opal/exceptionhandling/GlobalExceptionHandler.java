package com.flow.opal.exceptionhandling;

import com.flow.opal.exceptionhandling.customexceptions.ConnectionFailedException;
import com.flow.opal.exceptionhandling.customexceptions.UnauthorizedRequestException;
import javassist.NotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleAnyException(Exception ex, HttpServletRequest request) {
    return createResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({NullPointerException.class})
  public ResponseEntity<?> handleNullPointerException(NullPointerException ex,
      HttpServletRequest request) {
    return createResponse(ex, request, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handleNotFoundException(NotFoundException ex,
      HttpServletRequest request) {
    return createResponse(ex, request, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UnauthorizedRequestException.class)
  public ResponseEntity<?> handleUnAuthorizedRequestException(Exception ex,
      HttpServletRequest request) {
    return createResponse(ex, request, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> handleBadRequestException(Exception ex,
      HttpServletRequest request) {
    return createResponse(ex, request, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConnectionFailedException.class)
  public ResponseEntity<?> handleConnectionFailedException(ConnectionFailedException ex,
      HttpServletRequest request) {
    return createResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<?> handleUserNameNotFoundException(Exception ex,
      HttpServletRequest request) {
    return createResponse(ex,request,HttpStatus.NOT_FOUND);
  }

  public ResponseEntity createResponse(Exception ex, HttpServletRequest request,
      HttpStatus status) {
    String description = ex.getLocalizedMessage();

    if (description == null) {
      description = ex.toString();
    }

    ErrorResponse errorResponse = new ErrorResponse(status,
        description, request.getRequestURL().toString());

    return ResponseEntity.status(status).body(errorResponse);
  }
}
