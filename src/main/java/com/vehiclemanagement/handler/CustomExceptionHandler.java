package com.vehiclemanagement.handler;

import com.vehiclemanagement.exception.VehicleServiceException;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errors);
  }

  @ExceptionHandler(VehicleServiceException.class)
  public ResponseEntity<Map<String, String>> handleVehicleServiceException(
      VehicleServiceException ex) {

    Map<String, String> response = new HashMap<>();

    response.put("error", ex.getMessage());
    response.put("field", ex.getField());
    response.put("value", ex.getValue());
    response.put("status", String.valueOf(ex.getHttpStatus().value()));

    return ResponseEntity
        .status(ex.getHttpStatus())
        .body(response);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Map<String, Object>> handleAuthenticationException(
      AuthenticationException ex,
      WebRequest request
  ) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Map<String, Object>> handleAccessDeniedException(
      AccessDeniedException ex,
      WebRequest request
  ) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request));
  }

  private Map<String, Object> buildErrorResponse(
      HttpStatus status,
      String message,
      WebRequest request
  ) {
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("timestamp", Instant.now().toString());
    response.put("status", status.value());
    response.put("error", status.getReasonPhrase());
    response.put("message", message);
    response.put("path", request.getDescription(false).replace("uri=", ""));
    return response;
  }
}
