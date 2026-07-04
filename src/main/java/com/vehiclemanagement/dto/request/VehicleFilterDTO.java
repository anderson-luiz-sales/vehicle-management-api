package com.vehiclemanagement.dto.request;

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
public class VehicleFilterDTO {

  @Schema(description = "Marca do veículo", example = "Toyota")
  private String brand;

  @Schema(description = "Ano do veículo", example = "2024")
  private Integer year;

  @Schema(description = "Cor do veículo", example = "Preto")
  private String color;

  @Schema(description = "Preço mínimo", example = "20000")
  private BigDecimal minPrice;

  @Schema(description = "Preço máximo", example = "40000")
  private BigDecimal maxPrice;

}