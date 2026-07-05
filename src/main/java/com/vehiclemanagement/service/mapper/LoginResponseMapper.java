package com.vehiclemanagement.service.mapper;

import com.vehiclemanagement.dto.response.LoginResponseDTO;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponseMapper {

  public static LoginResponseDTO mapToLoginResponse(
      String accessToken,
      long expiresIn,
      UserDetails userDetails
  ) {
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    return LoginResponseDTO.builder()
        .accessToken(accessToken)
        .tokenType("Bearer")
        .expiresIn(expiresIn)
        .email(userDetails.getUsername())
        .roles(roles)
        .build();
  }
}
