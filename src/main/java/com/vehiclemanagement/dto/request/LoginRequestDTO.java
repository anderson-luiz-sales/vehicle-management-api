package com.vehiclemanagement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

  @Email
  @NotBlank
  @Schema(description = "Email do usuário", example = "admin@vehiclemanagement.com")
  private String email;

  @NotBlank
  @Schema(description = "Senha do usuário", example = "admin123")
  private String password;
}
