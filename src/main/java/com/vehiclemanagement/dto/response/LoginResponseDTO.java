package com.vehiclemanagement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

  @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiJ9...")
  private String accessToken;

  @Schema(description = "Tipo do token", example = "Bearer")
  private String tokenType;

  @Schema(description = "Tempo de expiração em milissegundos", example = "86400000")
  private Long expiresIn;

  @Schema(description = "Email autenticado", example = "admin@vehiclemanagement.com")
  private String email;

  @Schema(description = "Perfis do usuário")
  private List<String> roles;
}
