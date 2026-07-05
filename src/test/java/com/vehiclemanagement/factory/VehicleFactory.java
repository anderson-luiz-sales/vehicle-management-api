package com.vehiclemanagement.factory;

import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VehicleFactory {

  public static Vehicle makeVehicle() {
    return Vehicle.builder()
        .id(1L)
        .brand("Toyota")
        .model("Corolla")
        .year(2024)
        .color("Preto")
        .licensePlate("ABC1D23")
        .priceUsd(new BigDecimal("25000.00"))
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }

  public static Vehicle makeVehicleHonda() {
    return Vehicle.builder()
        .id(2L)
        .brand("Honda")
        .model("Civic")
        .year(2023)
        .color("Branco")
        .licensePlate("XYZ9E45")
        .priceUsd(new BigDecimal("23000.00"))
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }

  public static Vehicle makeVehicleFord() {
    return Vehicle.builder()
        .id(3L)
        .brand("Ford")
        .model("Mustang")
        .year(2022)
        .color("Vermelho")
        .licensePlate("MUS7ANG")
        .priceUsd(new BigDecimal("55000.00"))
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }

  public static Vehicle makeVehicleVolkswagen() {
    return Vehicle.builder()
        .id(4L)
        .brand("Volkswagen")
        .model("Golf GTI")
        .year(2024)
        .color("Azul")
        .licensePlate("G0LFGTI")
        .priceUsd(new BigDecimal("38000.00"))
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }

  public static Vehicle makeVehicleBMW() {
    return Vehicle.builder()
        .id(5L)
        .brand("BMW")
        .model("320i")
        .year(2023)
        .color("Prata")
        .licensePlate("BMW320I")
        .priceUsd(new BigDecimal("49000.00"))
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }

  public static Vehicle makeVehicleInactive() {
    return Vehicle.builder()
        .id(1L)
        .brand("Toyota")
        .model("Corolla")
        .year(2024)
        .color("Preto")
        .licensePlate("ABC1D23")
        .priceUsd(new BigDecimal("25000.00"))
        .active(false)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }
}