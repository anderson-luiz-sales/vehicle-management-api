package com.vehiclemanagement.service.mapper;

import com.vehiclemanagement.dto.request.VehicleFilterDTO;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleFilterMapper {

  public static VehicleFilterDTO mapToVehicleFilter(
      String brand,
      Integer year,
      String color,
      BigDecimal minPrice,
      BigDecimal maxPrice) {

    return VehicleFilterDTO.builder()
        .brand(brand)
        .year(year)
        .color(color)
        .minPrice(minPrice)
        .maxPrice(maxPrice)
        .build();

  }

}
