package com.vehiclemanagement.service.mapper;

import static com.vehiclemanagement.service.mapper.LoginResponseMapper.mapToLoginResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vehiclemanagement.dto.response.LoginResponseDTO;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class LoginResponseMapperTest {

  private static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkB2ZWhpY2xlbWFuYWdlbWVudC5jb20iLCJpYXQiOjE3MDAwMDAwMDAsImV4cCI6MTcwMDA4NjQwMH0.signature";
  private static final long EXPIRES_IN = 86400000L;
  private static final String EMAIL = "admin@vehiclemanagement.com";

  @Test
  void mapToLoginResponse_WithAdminUser_ShouldReturnLoginResponseDTO() {
    UserDetails userDetails = User.builder()
        .username(EMAIL)
        .password("admin123")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
        .build();

    LoginResponseDTO result = mapToLoginResponse(ACCESS_TOKEN, EXPIRES_IN, userDetails);

    assertNotNull(result);
    assertEquals(ACCESS_TOKEN, result.getAccessToken());
    assertEquals("Bearer", result.getTokenType());
    assertEquals(EXPIRES_IN, result.getExpiresIn());
    assertEquals(EMAIL, result.getEmail());
    assertNotNull(result.getRoles());
    assertEquals(1, result.getRoles().size());
    assertTrue(result.getRoles().contains("ROLE_ADMIN"));
  }

  @Test
  void mapToLoginResponse_WithUserRole_ShouldReturnLoginResponseDTO() {
    UserDetails userDetails = User.builder()
        .username("user@vehiclemanagement.com")
        .password("user123")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
        .build();

    LoginResponseDTO result = mapToLoginResponse(ACCESS_TOKEN, EXPIRES_IN, userDetails);

    assertNotNull(result);
    assertEquals(ACCESS_TOKEN, result.getAccessToken());
    assertEquals("Bearer", result.getTokenType());
    assertEquals(EXPIRES_IN, result.getExpiresIn());
    assertEquals("user@vehiclemanagement.com", result.getEmail());
    assertNotNull(result.getRoles());
    assertEquals(1, result.getRoles().size());
    assertTrue(result.getRoles().contains("ROLE_USER"));
  }

  @Test
  void mapToLoginResponse_WithMultipleRoles_ShouldReturnLoginResponseDTO() {
    UserDetails userDetails = User.builder()
        .username(EMAIL)
        .password("admin123")
        .authorities(Arrays.asList(
            new SimpleGrantedAuthority("ROLE_ADMIN"),
            new SimpleGrantedAuthority("ROLE_USER")
        ))
        .build();

    LoginResponseDTO result = mapToLoginResponse(ACCESS_TOKEN, EXPIRES_IN, userDetails);

    assertNotNull(result);
    assertEquals(ACCESS_TOKEN, result.getAccessToken());
    assertEquals("Bearer", result.getTokenType());
    assertEquals(EXPIRES_IN, result.getExpiresIn());
    assertEquals(EMAIL, result.getEmail());
    assertNotNull(result.getRoles());
    assertEquals(2, result.getRoles().size());
    assertTrue(result.getRoles().contains("ROLE_ADMIN"));
    assertTrue(result.getRoles().contains("ROLE_USER"));
  }

  @Test
  void mapToLoginResponse_WithNoRoles_ShouldReturnLoginResponseDTOWithEmptyRoles() {
    UserDetails userDetails = User.builder()
        .username(EMAIL)
        .password("admin123")
        .authorities(Collections.emptyList())
        .build();

    LoginResponseDTO result = mapToLoginResponse(ACCESS_TOKEN, EXPIRES_IN, userDetails);

    assertNotNull(result);
    assertEquals(ACCESS_TOKEN, result.getAccessToken());
    assertEquals("Bearer", result.getTokenType());
    assertEquals(EXPIRES_IN, result.getExpiresIn());
    assertEquals(EMAIL, result.getEmail());
    assertNotNull(result.getRoles());
    assertTrue(result.getRoles().isEmpty());
  }

  @Test
  void mapToLoginResponse_WithDifferentToken_ShouldReturnLoginResponseDTO() {
    String differentToken = "different.token.here";
    UserDetails userDetails = User.builder()
        .username(EMAIL)
        .password("admin123")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
        .build();

    LoginResponseDTO result = mapToLoginResponse(differentToken, EXPIRES_IN, userDetails);

    assertNotNull(result);
    assertEquals(differentToken, result.getAccessToken());
    assertEquals("Bearer", result.getTokenType());
    assertEquals(EXPIRES_IN, result.getExpiresIn());
    assertEquals(EMAIL, result.getEmail());
    assertNotNull(result.getRoles());
  }

  @Test
  void mapToLoginResponse_WithDifferentExpiration_ShouldReturnLoginResponseDTO() {
    long differentExpiration = 3600000L;
    UserDetails userDetails = User.builder()
        .username(EMAIL)
        .password("admin123")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
        .build();

    LoginResponseDTO result = mapToLoginResponse(ACCESS_TOKEN, differentExpiration, userDetails);

    assertNotNull(result);
    assertEquals(ACCESS_TOKEN, result.getAccessToken());
    assertEquals("Bearer", result.getTokenType());
    assertEquals(differentExpiration, result.getExpiresIn());
    assertEquals(EMAIL, result.getEmail());
    assertNotNull(result.getRoles());
  }

  @Test
  void mapToLoginResponse_WithDifferentEmail_ShouldReturnLoginResponseDTO() {
    String differentEmail = "different@email.com";
    UserDetails userDetails = User.builder()
        .username(differentEmail)
        .password("password123")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
        .build();

    LoginResponseDTO result = mapToLoginResponse(ACCESS_TOKEN, EXPIRES_IN, userDetails);

    assertNotNull(result);
    assertEquals(ACCESS_TOKEN, result.getAccessToken());
    assertEquals("Bearer", result.getTokenType());
    assertEquals(EXPIRES_IN, result.getExpiresIn());
    assertEquals(differentEmail, result.getEmail());
    assertNotNull(result.getRoles());
    assertTrue(result.getRoles().contains("ROLE_USER"));
  }

  @Test
  void mapToLoginResponse_ShouldAlwaysReturnBearerTokenType() {
    UserDetails userDetails = User.builder()
        .username(EMAIL)
        .password("admin123")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
        .build();

    LoginResponseDTO result1 = mapToLoginResponse(ACCESS_TOKEN, EXPIRES_IN, userDetails);
    LoginResponseDTO result2 = mapToLoginResponse("different.token", EXPIRES_IN, userDetails);

    assertEquals("Bearer", result1.getTokenType());
    assertEquals("Bearer", result2.getTokenType());
  }

  @Test
  void mapToLoginResponse_WithCustomAuthority_ShouldReturnLoginResponseDTO() {
    UserDetails userDetails = User.builder()
        .username(EMAIL)
        .password("admin123")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("CUSTOM_ROLE")))
        .build();

    LoginResponseDTO result = mapToLoginResponse(ACCESS_TOKEN, EXPIRES_IN, userDetails);

    assertNotNull(result);
    assertNotNull(result.getRoles());
    assertEquals(1, result.getRoles().size());
    assertTrue(result.getRoles().contains("CUSTOM_ROLE"));
  }
}