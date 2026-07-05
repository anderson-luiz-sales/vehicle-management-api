package com.vehiclemanagement.controller;

import com.vehiclemanagement.dto.request.LoginRequestDTO;
import com.vehiclemanagement.dto.response.LoginResponseDTO;
import com.vehiclemanagement.factory.LoginRequestDTOFactory;
import com.vehiclemanagement.factory.LoginResponseDTOFactory;
import com.vehiclemanagement.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  @InjectMocks
  private AuthController authController;

  @Mock
  private AuthService authService;

  private LoginRequestDTO loginRequestDTO;
  private LoginResponseDTO loginResponseDTO;

  @BeforeEach
  void setUp() {
    loginRequestDTO = LoginRequestDTOFactory.makeLoginRequestDTO();
    loginResponseDTO = LoginResponseDTOFactory.makeLoginResponseDTO();
  }

  @Test
  void login_WithValidCredentials_ShouldReturnLoginResponse() {
    when(authService.login(loginRequestDTO)).thenReturn(loginResponseDTO);

    ResponseEntity<LoginResponseDTO> response = authController.login(loginRequestDTO);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(loginResponseDTO, response.getBody());
    assertEquals("admin@vehiclemanagement.com", response.getBody().getEmail());
    assertEquals("Bearer", response.getBody().getTokenType());
    assertNotNull(response.getBody().getAccessToken());
    assertEquals(86400000L, response.getBody().getExpiresIn());

    verify(authService, times(1)).login(loginRequestDTO);
  }

  @Test
  void login_WithUserCredentials_ShouldReturnUserLoginResponse() {
    LoginRequestDTO userRequest = LoginRequestDTOFactory.makeLoginRequestDTOUser();
    LoginResponseDTO userResponse = LoginResponseDTOFactory.makeLoginResponseDTOUser();

    when(authService.login(userRequest)).thenReturn(userResponse);

    ResponseEntity<LoginResponseDTO> response = authController.login(userRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("user@vehiclemanagement.com", response.getBody().getEmail());
    assertEquals(1, response.getBody().getRoles().size());
    assertEquals("USER", response.getBody().getRoles().get(0));

    verify(authService, times(1)).login(userRequest);
  }

  @Test
  void login_WithInvalidEmail_ShouldReturnError() {
    LoginRequestDTO invalidRequest = LoginRequestDTOFactory.makeLoginRequestDTOInvalidEmail();

    when(authService.login(invalidRequest))
        .thenThrow(new RuntimeException("Invalid email format"));

    try {
      authController.login(invalidRequest);
    } catch (RuntimeException e) {
      assertEquals("Invalid email format", e.getMessage());
    }

    verify(authService, times(1)).login(invalidRequest);
  }

  @Test
  void login_WithEmptyPassword_ShouldReturnError() {
    LoginRequestDTO emptyPasswordRequest = LoginRequestDTOFactory.makeLoginRequestDTOEmptyPassword();

    when(authService.login(emptyPasswordRequest))
        .thenThrow(new RuntimeException("Password cannot be empty"));

    try {
      authController.login(emptyPasswordRequest);
    } catch (RuntimeException e) {
      assertEquals("Password cannot be empty", e.getMessage());
    }

    verify(authService, times(1)).login(emptyPasswordRequest);
  }

  @Test
  void login_WithInvalidCredentials_ShouldReturnUnauthorized() {
    LoginRequestDTO invalidCredentials = LoginRequestDTO.builder()
        .email("wrong@email.com")
        .password("wrongpassword")
        .build();

    when(authService.login(invalidCredentials))
        .thenThrow(new RuntimeException("Invalid credentials"));

    try {
      authController.login(invalidCredentials);
    } catch (RuntimeException e) {
      assertEquals("Invalid credentials", e.getMessage());
    }

    verify(authService, times(1)).login(invalidCredentials);
  }

  @Test
  void login_ShouldReturnTokenWithCorrectType() {
    when(authService.login(loginRequestDTO)).thenReturn(loginResponseDTO);

    ResponseEntity<LoginResponseDTO> response = authController.login(loginRequestDTO);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Bearer", response.getBody().getTokenType());
    assertNotNull(response.getBody().getAccessToken());
    verify(authService, times(1)).login(loginRequestDTO);
  }

  @Test
  void login_ShouldReturnCorrectExpirationTime() {
    when(authService.login(loginRequestDTO)).thenReturn(loginResponseDTO);

    ResponseEntity<LoginResponseDTO> response = authController.login(loginRequestDTO);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(86400000L, response.getBody().getExpiresIn());
    verify(authService, times(1)).login(loginRequestDTO);
  }
}