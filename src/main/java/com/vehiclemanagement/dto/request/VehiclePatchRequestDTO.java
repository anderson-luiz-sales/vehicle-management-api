package com.vehiclemanagement.dto.request;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiclePatchRequestDTO {

  private String brand;
  private String model;
  private Integer year;
  private String color;
  private String licensePlate;

  @Positive
  private BigDecimal priceBrl;
}
