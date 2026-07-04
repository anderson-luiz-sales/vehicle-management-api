package com.vehiclemanagement.service.mapper;

import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import com.vehiclemanagement.entity.Vehicle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleResponseMapper {

  public static VehicleResponseDTO mapToVehicleResponse(Vehicle vehicle) {

    return VehicleResponseDTO.builder()
        .id(vehicle.getId())
        .brand(vehicle.getBrand())
        .model(vehicle.getModel())
        .year(vehicle.getYear())
        .color(vehicle.getColor())
        .licensePlate(vehicle.getLicensePlate())
        .priceUsd(vehicle.getPriceUsd())
        .build();
  }

}