package com.vehiclemanagement.handler;

import com.vehiclemanagement.exception.VehicleServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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

}