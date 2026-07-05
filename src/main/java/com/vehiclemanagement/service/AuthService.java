package com.vehiclemanagement.service;

import static com.vehiclemanagement.service.mapper.LoginResponseMapper.mapToLoginResponse;

import com.vehiclemanagement.dto.request.LoginRequestDTO;
import com.vehiclemanagement.dto.response.LoginResponseDTO;
import com.vehiclemanagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public LoginResponseDTO login(LoginRequestDTO request) {
    Authentication authentication = authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken.unauthenticated(
            request.getEmail(),
            request.getPassword()
        )
    );

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String accessToken = jwtService.generateToken(userDetails);

    return mapToLoginResponse(accessToken, jwtService.getExpirationMs(), userDetails);
  }
}
