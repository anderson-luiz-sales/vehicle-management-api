package com.vehiclemanagement.service.mapper;

import com.vehiclemanagement.dto.request.VehiclePatchRequestDTO;
import com.vehiclemanagement.dto.request.VehicleRequestDTO;
import com.vehiclemanagement.entity.Vehicle;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleMapper {

  public static Vehicle mapToVehicle(
      VehicleRequestDTO request,
      BigDecimal priceUsd
  ) {
    return Vehicle.builder()
        .brand(request.getBrand())
        .model(request.getModel())
        .year(request.getYear())
        .color(request.getColor())
        .licensePlate(request.getLicensePlate())
        .priceUsd(priceUsd)
        .active(true)
        .build();
  }

  public static void updateVehicle(
      Vehicle vehicle,
      VehicleRequestDTO request,
      BigDecimal priceUsd
  ) {

    vehicle.setBrand(request.getBrand());
    vehicle.setModel(request.getModel());
    vehicle.setYear(request.getYear());
    vehicle.setColor(request.getColor());
    vehicle.setLicensePlate(request.getLicensePlate());
    vehicle.setPriceUsd(priceUsd);

  }

  public static void patchVehicle(
      Vehicle vehicle,
      VehiclePatchRequestDTO request,
      BigDecimal priceUsd
  ) {

    vehicle.setBrand(request.getBrand() != null ? request.getBrand() : vehicle.getBrand());
    vehicle.setModel(request.getModel() != null ? request.getModel() : vehicle.getModel());
    vehicle.setYear(request.getYear() != null ? request.getYear() : vehicle.getYear());
    vehicle.setColor(request.getColor() != null ? request.getColor() : vehicle.getColor());
    vehicle.setLicensePlate(request.getLicensePlate() != null
        ? request.getLicensePlate() : vehicle.getLicensePlate());
    vehicle.setPriceUsd(priceUsd != null ? priceUsd : vehicle.getPriceUsd());
  }

}