package com.vehiclemanagement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Builder)
public class VehicleRequestDTO {

  @Schema(example = "Toyota")
  @NotBlank(message = "O campo 'brand' é obrigatório.")
  @Size(max = 100)
  private String brand;

  @Schema(example = "Corolla")
  @NotBlank(message = "O campo 'model' é obrigatório.")
  @Size(max = 100)
  private String model;

  @Schema(example = "2024")
  @NotNull(message = "O campo 'year' é obrigatório.")
  @Min(1900)
  private Integer year;

  @Schema(example = "Preto")
  @NotBlank(message = "O campo 'color' é obrigatório.")
  private String color;

  @Schema(example = "ABC1D23")
  @NotBlank(message = "O campo 'licensePlate' é obrigatório.")
  private String licensePlate;

  @Schema(example = "120000.00")
  @NotNull(message = "O campo 'priceBrl' é obrigatório.")
  @DecimalMin("0.01")
  private BigDecimal priceBrl;

}