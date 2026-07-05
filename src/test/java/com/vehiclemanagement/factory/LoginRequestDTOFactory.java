package com.vehiclemanagement.factory;

import com.vehiclemanagement.dto.request.LoginRequestDTO;

public class LoginRequestDTOFactory {

  public static LoginRequestDTO makeLoginRequestDTO() {
    return LoginRequestDTO.builder()
        .email("admin@vehiclemanagement.com")
        .password("admin123")
        .build();
  }

  public static LoginRequestDTO makeLoginRequestDTOUser() {
    return LoginRequestDTO.builder()
        .email("user@vehiclemanagement.com")
        .password("user123")
        .build();
  }

  public static LoginRequestDTO makeLoginRequestDTOInvalidEmail() {
    return LoginRequestDTO.builder()
        .email("invalid-email")
        .password("password123")
        .build();
  }

  public static LoginRequestDTO makeLoginRequestDTOEmptyPassword() {
    return LoginRequestDTO.builder()
        .email("user@vehiclemanagement.com")
        .password("")
        .build();
  }
}