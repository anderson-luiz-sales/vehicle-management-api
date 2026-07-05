package com.vehiclemanagement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Builder)
public class VehicleBrandReportResponseDTO {

  @Schema(description = "Marca do veículo", example = "Toyota")
  private String brand;

  @Schema(description = "Quantidade de veículos", example = "15")
  private Long quantity;

}