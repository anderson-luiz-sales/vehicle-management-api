package com.vehiclemanagement.factory;

import com.vehiclemanagement.dto.response.LoginResponseDTO;
import java.util.Arrays;
import java.util.List;

public class LoginResponseDTOFactory {

  public static LoginResponseDTO makeLoginResponseDTO() {
    return LoginResponseDTO.builder()
        .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkB2ZWhpY2xlbWFuYWdlbWVudC5jb20iLCJpYXQiOjE3MDAwMDAwMDAsImV4cCI6MTcwMDA4NjQwMH0.signature")
        .tokenType("Bearer")
        .expiresIn(86400000L)
        .email("admin@vehiclemanagement.com")
        .roles(Arrays.asList("ADMIN", "USER"))
        .build();
  }

  public static LoginResponseDTO makeLoginResponseDTOUser() {
    return LoginResponseDTO.builder()
        .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQHZlaGljbGVtYW5hZ2VtZW50LmNvbSIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDg2NDAwfQ.signature")
        .tokenType("Bearer")
        .expiresIn(86400000L)
        .email("user@vehiclemanagement.com")
        .roles(List.of("USER"))
        .build();
  }
}