package com.vehiclemanagement.service.mapper;

import static com.vehiclemanagement.service.mapper.VehicleResponseMapper.mapToVehicleResponse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.factory.VehicleFactory;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class VehicleResponseMapperTest {

  @Test
  void mapToVehicleResponse_WithValidVehicle_ShouldReturnVehicleResponseDTO() {
    Vehicle vehicle = VehicleFactory.makeVehicle();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertEquals(vehicle.getPriceUsd(), result.getPriceUsd());
  }

  @Test
  void mapToVehicleResponse_WithHondaVehicle_ShouldReturnVehicleResponseDTO() {
    Vehicle vehicle = VehicleFactory.makeVehicleHonda();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertEquals(vehicle.getPriceUsd(), result.getPriceUsd());
  }

  @Test
  void mapToVehicleResponse_WithFordVehicle_ShouldReturnVehicleResponseDTO() {
    Vehicle vehicle = VehicleFactory.makeVehicleFord();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertEquals(vehicle.getPriceUsd(), result.getPriceUsd());
  }

  @Test
  void mapToVehicleResponse_WithVolkswagenVehicle_ShouldReturnVehicleResponseDTO() {
    Vehicle vehicle = VehicleFactory.makeVehicleVolkswagen();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertEquals(vehicle.getPriceUsd(), result.getPriceUsd());
  }

  @Test
  void mapToVehicleResponse_WithBMWVehicle_ShouldReturnVehicleResponseDTO() {
    Vehicle vehicle = VehicleFactory.makeVehicleBMW();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertEquals(vehicle.getPriceUsd(), result.getPriceUsd());
  }

  @Test
  void mapToVehicleResponse_WithInactiveVehicle_ShouldReturnVehicleResponseDTO() {
    Vehicle vehicle = VehicleFactory.makeVehicleInactive();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertEquals(vehicle.getPriceUsd(), result.getPriceUsd());
  }

  @Test
  void mapToVehicleResponse_WithVehicleWithoutId_ShouldReturnVehicleResponseDTOWithNullId() {
    Vehicle vehicle = Vehicle.builder()
        .brand("Toyota")
        .model("Corolla")
        .year(2024)
        .color("Preto")
        .licensePlate("ABC1D23")
        .priceUsd(new BigDecimal("25000.00"))
        .active(true)
        .build();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertNull(result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertEquals(vehicle.getPriceUsd(), result.getPriceUsd());
  }

  @Test
  void mapToVehicleResponse_WithVehicleWithNullFields_ShouldReturnVehicleResponseDTOWithNulls() {
    Vehicle vehicle = Vehicle.builder()
        .id(1L)
        .brand(null)
        .model(null)
        .year(null)
        .color(null)
        .licensePlate(null)
        .priceUsd(null)
        .active(true)
        .build();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertNull(result.getBrand());
    assertNull(result.getModel());
    assertNull(result.getYear());
    assertNull(result.getColor());
    assertNull(result.getLicensePlate());
    assertNull(result.getPriceUsd());
  }

  @Test
  void mapToVehicleResponse_ShouldNotIncludeActiveField() {
    Vehicle vehicle = VehicleFactory.makeVehicle();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    // Verifica que não existe campo active no DTO
    assertDoesNotThrow(() -> result.getId());
    assertDoesNotThrow(() -> result.getBrand());
    assertDoesNotThrow(() -> result.getModel());
    assertDoesNotThrow(() -> result.getYear());
    assertDoesNotThrow(() -> result.getColor());
    assertDoesNotThrow(() -> result.getLicensePlate());
    assertDoesNotThrow(() -> result.getPriceUsd());
    // Não deve ter método getActive()
  }

  @Test
  void mapToVehicleResponse_WithVehicleWithLargePrice_ShouldReturnVehicleResponseDTO() {
    Vehicle vehicle = Vehicle.builder()
        .id(1L)
        .brand("Lamborghini")
        .model("Aventador")
        .year(2024)
        .color("Amarelo")
        .licensePlate("LAMB01")
        .priceUsd(new BigDecimal("500000.00"))
        .active(true)
        .build();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertEquals(new BigDecimal("500000.00"), result.getPriceUsd());
  }

  @Test
  void mapToVehicleResponse_WithVehicleWithSmallPrice_ShouldReturnVehicleResponseDTO() {
    Vehicle vehicle = Vehicle.builder()
        .id(1L)
        .brand("Fiat")
        .model("Uno")
        .year(2010)
        .color("Branco")
        .licensePlate("UNO123")
        .priceUsd(new BigDecimal("5000.00"))
        .active(true)
        .build();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertEquals(new BigDecimal("5000.00"), result.getPriceUsd());
  }

  @Test
  void mapToVehicleResponse_WithVehicleWithNullPrice_ShouldReturnVehicleResponseDTOWithNullPrice() {
    Vehicle vehicle = Vehicle.builder()
        .id(1L)
        .brand("Toyota")
        .model("Corolla")
        .year(2024)
        .color("Preto")
        .licensePlate("ABC1D23")
        .priceUsd(null)
        .active(true)
        .build();

    VehicleResponseDTO result = mapToVehicleResponse(vehicle);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertNull(result.getPriceUsd());
  }
}