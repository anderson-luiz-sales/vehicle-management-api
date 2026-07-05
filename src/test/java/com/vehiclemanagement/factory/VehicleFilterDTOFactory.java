package com.vehiclemanagement.factory;

import com.vehiclemanagement.dto.request.VehicleFilterDTO;
import java.math.BigDecimal;

public class VehicleFilterDTOFactory {

  public static VehicleFilterDTO makeVehicleFilterDTO() {
    return VehicleFilterDTO.builder()
        .brand("Toyota")
        .year(2024)
        .color("Preto")
        .minPrice(new BigDecimal("100000.00"))
        .maxPrice(new BigDecimal("200000.00"))
        .build();
  }
}