package com.vehiclemanagement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Builder)
public class VehicleResponseDTO {

  @Schema(description = "Identificador do veículo", example = "1")
  private Long id;

  @Schema(description = "Marca do veículo", example = "Toyota")
  private String brand;

  @Schema(description = "Modelo do veículo", example = "Corolla")
  private String model;

  @Schema(description = "Ano do veículo", example = "2024")
  private Integer year;

  @Schema(description = "Cor do veículo", example = "Preto")
  private String color;

  @Schema(description = "Placa do veículo", example = "ABC1D23")
  private String licensePlate;

  @Schema(description = "Preço em dólar", example = "25000.00")
  private BigDecimal priceUsd;
}
