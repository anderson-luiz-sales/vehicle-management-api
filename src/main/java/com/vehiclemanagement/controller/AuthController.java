package com.vehiclemanagement.controller;

import com.vehiclemanagement.controller.swagger.AuthControllerDoc;
import com.vehiclemanagement.dto.request.LoginRequestDTO;
import com.vehiclemanagement.dto.response.LoginResponseDTO;
import com.vehiclemanagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDoc {

  private final AuthService authService;

  @Override
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
    return ResponseEntity.ok(authService.login(request));
  }
}
