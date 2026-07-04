package com.vehiclemanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class VehicleServiceException extends RuntimeException {

  private final String field;
  private final String value;
  private final HttpStatus httpStatus;

  public VehicleServiceException(
      String field,
      String value,
      String message,
      HttpStatus httpStatus) {

    super(message);
    this.field = field;
    this.value = value;
    this.httpStatus = httpStatus;
  }

}