package com.vehiclemanagement.service;

import com.vehiclemanagement.dto.request.LoginRequestDTO;
import com.vehiclemanagement.dto.response.LoginResponseDTO;
import com.vehiclemanagement.factory.LoginRequestDTOFactory;
import com.vehiclemanagement.factory.LoginResponseDTOFactory;
import com.vehiclemanagement.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @InjectMocks
  private AuthService authService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtService jwtService;

  private LoginRequestDTO loginRequestDTO;
  private UserDetails userDetails;
  private Authentication authentication;
  private String accessToken;
  private Long expirationMs;

  @BeforeEach
  void setUp() {
    loginRequestDTO = LoginRequestDTOFactory.makeLoginRequestDTO();
    accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkB2ZWhpY2xlbWFuYWdlbWVudC5jb20iLCJpYXQiOjE3MDAwMDAwMDAsImV4cCI6MTcwMDA4NjQwMH0.signature";
    expirationMs = 86400000L;

    userDetails = User.builder()
        .username("admin@vehiclemanagement.com")
        .password("admin123")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
        .build();

    authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  @Test
  void login_WithValidCredentials_ShouldReturnLoginResponse() {
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(jwtService.generateToken(userDetails)).thenReturn(accessToken);
    when(jwtService.getExpirationMs()).thenReturn(expirationMs);

    LoginResponseDTO result = authService.login(loginRequestDTO);

    assertNotNull(result);
    assertEquals(accessToken, result.getAccessToken());
    assertEquals("Bearer", result.getTokenType());
    assertEquals(expirationMs, result.getExpiresIn());
    assertEquals(userDetails.getUsername(), result.getEmail());
    assertNotNull(result.getRoles());

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, times(1)).generateToken(userDetails);
    verify(jwtService, times(1)).getExpirationMs();
  }

  @Test
  void login_WithUserRole_ShouldReturnLoginResponseWithUserRole() {
    UserDetails userDetailsWithUserRole = User.builder()
        .username("user@vehiclemanagement.com")
        .password("user123")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
        .build();

    Authentication authenticationWithUserRole = new UsernamePasswordAuthenticationToken(
        userDetailsWithUserRole, null, userDetailsWithUserRole.getAuthorities());

    LoginRequestDTO userRequest = LoginRequestDTOFactory.makeLoginRequestDTOUser();

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authenticationWithUserRole);
    when(jwtService.generateToken(userDetailsWithUserRole)).thenReturn(accessToken);
    when(jwtService.getExpirationMs()).thenReturn(expirationMs);

    LoginResponseDTO result = authService.login(userRequest);

    assertNotNull(result);
    assertEquals(accessToken, result.getAccessToken());
    assertEquals("Bearer", result.getTokenType());
    assertEquals(expirationMs, result.getExpiresIn());
    assertEquals(userDetailsWithUserRole.getUsername(), result.getEmail());
    assertNotNull(result.getRoles());
    assertTrue(result.getRoles().stream().anyMatch(role -> role.equals("USER") || role.equals("ROLE_USER")));

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, times(1)).generateToken(userDetailsWithUserRole);
    verify(jwtService, times(1)).getExpirationMs();
  }

  @Test
  void login_WithAdminRole_ShouldReturnLoginResponseWithAdminRole() {
    UserDetails userDetailsWithAdminRole = User.builder()
        .username("admin@vehiclemanagement.com")
        .password("admin123")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
        .build();

    Authentication authenticationWithAdminRole = new UsernamePasswordAuthenticationToken(
        userDetailsWithAdminRole, null, userDetailsWithAdminRole.getAuthorities());

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authenticationWithAdminRole);
    when(jwtService.generateToken(userDetailsWithAdminRole)).thenReturn(accessToken);
    when(jwtService.getExpirationMs()).thenReturn(expirationMs);

    LoginResponseDTO result = authService.login(loginRequestDTO);

    assertNotNull(result);
    assertEquals(accessToken, result.getAccessToken());
    assertEquals("Bearer", result.getTokenType());
    assertEquals(expirationMs, result.getExpiresIn());
    assertEquals(userDetailsWithAdminRole.getUsername(), result.getEmail());
    assertNotNull(result.getRoles());
    assertTrue(result.getRoles().stream().anyMatch(role -> role.equals("ADMIN") || role.equals("ROLE_ADMIN")));

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, times(1)).generateToken(userDetailsWithAdminRole);
    verify(jwtService, times(1)).getExpirationMs();
  }

  @Test
  void login_WithInvalidCredentials_ShouldThrowException() {
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new RuntimeException("Invalid credentials"));

    assertThrows(RuntimeException.class, () -> authService.login(loginRequestDTO));

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, never()).generateToken(any(UserDetails.class));
    verify(jwtService, never()).getExpirationMs();
  }

  @Test
  void login_WithEmptyPassword_ShouldThrowException() {
    LoginRequestDTO emptyPasswordRequest = LoginRequestDTOFactory.makeLoginRequestDTOEmptyPassword();

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new RuntimeException("Bad credentials"));

    assertThrows(RuntimeException.class, () -> authService.login(emptyPasswordRequest));

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, never()).generateToken(any(UserDetails.class));
    verify(jwtService, never()).getExpirationMs();
  }

  @Test
  void login_WithInvalidEmail_ShouldThrowException() {
    LoginRequestDTO invalidEmailRequest = LoginRequestDTOFactory.makeLoginRequestDTOInvalidEmail();

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new RuntimeException("Invalid email"));

    assertThrows(RuntimeException.class, () -> authService.login(invalidEmailRequest));

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, never()).generateToken(any(UserDetails.class));
    verify(jwtService, never()).getExpirationMs();
  }

  @Test
  void login_ShouldReturnTokenWithCorrectType() {
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(jwtService.generateToken(userDetails)).thenReturn(accessToken);
    when(jwtService.getExpirationMs()).thenReturn(expirationMs);

    LoginResponseDTO result = authService.login(loginRequestDTO);

    assertNotNull(result);
    assertEquals("Bearer", result.getTokenType());
    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, times(1)).generateToken(userDetails);
    verify(jwtService, times(1)).getExpirationMs();
  }

  @Test
  void login_ShouldReturnCorrectExpirationTime() {
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(jwtService.generateToken(userDetails)).thenReturn(accessToken);
    when(jwtService.getExpirationMs()).thenReturn(expirationMs);

    LoginResponseDTO result = authService.login(loginRequestDTO);

    assertNotNull(result);
    assertEquals(expirationMs, result.getExpiresIn());
    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, times(1)).generateToken(userDetails);
    verify(jwtService, times(1)).getExpirationMs();
  }

  @Test
  void login_WithMultipleRoles_ShouldReturnAllRoles() {
    UserDetails userDetailsWithMultipleRoles = User.builder()
        .username("admin@vehiclemanagement.com")
        .password("admin123")
        .authorities(Arrays.asList(
            new SimpleGrantedAuthority("ROLE_ADMIN"),
            new SimpleGrantedAuthority("ROLE_USER")
        ))
        .build();

    Authentication authenticationWithMultipleRoles = new UsernamePasswordAuthenticationToken(
        userDetailsWithMultipleRoles, null, userDetailsWithMultipleRoles.getAuthorities());

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authenticationWithMultipleRoles);
    when(jwtService.generateToken(userDetailsWithMultipleRoles)).thenReturn(accessToken);
    when(jwtService.getExpirationMs()).thenReturn(expirationMs);

    LoginResponseDTO result = authService.login(loginRequestDTO);

    assertNotNull(result);
    assertNotNull(result.getRoles());
    assertTrue(result.getRoles().size() >= 1);
    assertTrue(result.getRoles().stream().anyMatch(role -> role.equals("ADMIN") || role.equals("ROLE_ADMIN")));

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, times(1)).generateToken(userDetailsWithMultipleRoles);
    verify(jwtService, times(1)).getExpirationMs();
  }
}