package com.vehiclemanagement.controller.swagger;

import com.vehiclemanagement.dto.request.LoginRequestDTO;
import com.vehiclemanagement.dto.response.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDoc {

  @Operation(
      summary = "Autenticar usuário",
      description = "Autentica com email e senha e retorna um token JWT."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso."),
      @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
  })
  ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request);
}
