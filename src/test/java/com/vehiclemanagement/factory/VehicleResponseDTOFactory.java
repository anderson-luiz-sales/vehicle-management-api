package com.vehiclemanagement.factory;

import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import java.math.BigDecimal;

public class VehicleResponseDTOFactory {

  public static VehicleResponseDTO makeVehicleResponseDTO() {
    return VehicleResponseDTO.builder()
        .id(1L)
        .brand("Toyota")
        .model("Corolla")
        .year(2024)
        .color("Preto")
        .licensePlate("ABC1D23")
        .priceUsd(new BigDecimal("25000.00"))
        .build();
  }

  public static VehicleResponseDTO makeVehicleResponseDTOPatched() {
    return VehicleResponseDTO.builder()
        .id(1L)
        .brand("Toyota")
        .model("Corolla")
        .year(2024)
        .color("Cinza")
        .licensePlate("ABC1D23")
        .priceUsd(new BigDecimal("26000.00"))
        .build();
  }
}