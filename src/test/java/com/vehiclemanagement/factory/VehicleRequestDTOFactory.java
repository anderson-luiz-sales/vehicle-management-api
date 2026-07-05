package com.vehiclemanagement.factory;

import com.vehiclemanagement.dto.request.VehicleRequestDTO;
import java.math.BigDecimal;

public class VehicleRequestDTOFactory {

  public static VehicleRequestDTO makeVehicleRequestDTO() {
    return VehicleRequestDTO.builder()
        .brand("Toyota")
        .model("Corolla")
        .year(2024)
        .color("Preto")
        .licensePlate("ABC1D23")
        .priceBrl(new BigDecimal("145000.00"))
        .build();
  }

  public static VehicleRequestDTO makeVehicleRequestDTOHonda() {
    return VehicleRequestDTO.builder()
        .brand("Honda")
        .model("Civic")
        .year(2023)
        .color("Branco")
        .licensePlate("XYZ9E45")
        .priceBrl(new BigDecimal("138000.00"))
        .build();
  }

  public static VehicleRequestDTO makeVehicleRequestDTOFord() {
    return VehicleRequestDTO.builder()
        .brand("Ford")
        .model("Mustang")
        .year(2022)
        .color("Vermelho")
        .licensePlate("MUS7ANG")
        .priceBrl(new BigDecimal("320000.00"))
        .build();
  }
}