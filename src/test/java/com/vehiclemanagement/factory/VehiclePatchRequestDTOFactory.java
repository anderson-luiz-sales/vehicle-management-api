package com.vehiclemanagement.factory;

import com.vehiclemanagement.dto.request.VehiclePatchRequestDTO;
import java.math.BigDecimal;

public class VehiclePatchRequestDTOFactory {

  public static VehiclePatchRequestDTO makeVehiclePatchRequestDTO() {
    return VehiclePatchRequestDTO.builder()
        .brand("Toyota")
        .model("Corolla")
        .year(2024)
        .color("Azul")
        .licensePlate("ABC1D23")
        .priceBrl(new BigDecimal("142000.00"))
        .build();
  }

  public static VehiclePatchRequestDTO makeVehiclePatchRequestPriceOnly() {
    return VehiclePatchRequestDTO.builder()
        .priceBrl(new BigDecimal("150000.00"))
        .build();
  }

  public static VehiclePatchRequestDTO makeVehiclePatchRequestColorOnly() {
    return VehiclePatchRequestDTO.builder()
        .color("Cinza")
        .build();
  }

  public static VehiclePatchRequestDTO makeVehiclePatchRequestComplete() {
    return VehiclePatchRequestDTO.builder()
        .brand("Toyota")
        .model("Camry")
        .year(2025)
        .color("Prata")
        .licensePlate("CAM25RY")
        .priceBrl(new BigDecimal("185000.00"))
        .build();
  }
}