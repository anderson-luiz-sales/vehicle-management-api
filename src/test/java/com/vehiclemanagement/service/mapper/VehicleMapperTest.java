package com.vehiclemanagement.service.mapper;

import com.vehiclemanagement.dto.request.VehiclePatchRequestDTO;
import com.vehiclemanagement.dto.request.VehicleRequestDTO;
import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.factory.VehiclePatchRequestDTOFactory;
import com.vehiclemanagement.factory.VehicleRequestDTOFactory;
import com.vehiclemanagement.factory.VehicleFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.vehiclemanagement.service.mapper.VehicleMapper.*;
import static org.junit.jupiter.api.Assertions.*;

class VehicleMapperTest {

  private static final BigDecimal PRICE_USD = new BigDecimal("25000.00");
  private static final BigDecimal UPDATED_PRICE_USD = new BigDecimal("23000.00");
  private static final BigDecimal PATCH_PRICE_USD = new BigDecimal("26000.00");

  @Test
  void mapToVehicle_WithValidRequest_ShouldReturnVehicle() {
    VehicleRequestDTO request = VehicleRequestDTOFactory.makeVehicleRequestDTO();

    Vehicle result = mapToVehicle(request, PRICE_USD);

    assertNotNull(result);
    assertEquals(request.getBrand(), result.getBrand());
    assertEquals(request.getModel(), result.getModel());
    assertEquals(request.getYear(), result.getYear());
    assertEquals(request.getColor(), result.getColor());
    assertEquals(request.getLicensePlate(), result.getLicensePlate());
    assertEquals(PRICE_USD, result.getPriceUsd());
    assertTrue(result.getActive());
    assertNull(result.getId());
    assertNull(result.getCreatedAt());
    assertNull(result.getUpdatedAt());
  }

  @Test
  void mapToVehicle_WithDifferentRequest_ShouldReturnVehicle() {
    VehicleRequestDTO request = VehicleRequestDTOFactory.makeVehicleRequestDTOHonda();

    Vehicle result = mapToVehicle(request, UPDATED_PRICE_USD);

    assertNotNull(result);
    assertEquals(request.getBrand(), result.getBrand());
    assertEquals(request.getModel(), result.getModel());
    assertEquals(request.getYear(), result.getYear());
    assertEquals(request.getColor(), result.getColor());
    assertEquals(request.getLicensePlate(), result.getLicensePlate());
    assertEquals(UPDATED_PRICE_USD, result.getPriceUsd());
    assertTrue(result.getActive());
  }

  @Test
  void updateVehicle_WithValidRequest_ShouldUpdateAllFields() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehicleRequestDTO request = VehicleRequestDTOFactory.makeVehicleRequestDTOHonda();

    updateVehicle(vehicle, request, UPDATED_PRICE_USD);

    assertEquals(request.getBrand(), vehicle.getBrand());
    assertEquals(request.getModel(), vehicle.getModel());
    assertEquals(request.getYear(), vehicle.getYear());
    assertEquals(request.getColor(), vehicle.getColor());
    assertEquals(request.getLicensePlate(), vehicle.getLicensePlate());
    assertEquals(UPDATED_PRICE_USD, vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
    assertNotNull(vehicle.getId());
    assertNotNull(vehicle.getCreatedAt());
    assertNotNull(vehicle.getUpdatedAt());
  }

  @Test
  void updateVehicle_WithDifferentRequest_ShouldUpdateAllFields() {
    Vehicle vehicle = VehicleFactory.makeVehicleHonda();
    VehicleRequestDTO request = VehicleRequestDTOFactory.makeVehicleRequestDTOFord();

    updateVehicle(vehicle, request, PRICE_USD);

    assertEquals(request.getBrand(), vehicle.getBrand());
    assertEquals(request.getModel(), vehicle.getModel());
    assertEquals(request.getYear(), vehicle.getYear());
    assertEquals(request.getColor(), vehicle.getColor());
    assertEquals(request.getLicensePlate(), vehicle.getLicensePlate());
    assertEquals(PRICE_USD, vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
  }

  @Test
  void patchVehicle_WithAllFields_ShouldUpdateAllFields() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehiclePatchRequestDTO request = VehiclePatchRequestDTOFactory.makeVehiclePatchRequestComplete();

    patchVehicle(vehicle, request, PATCH_PRICE_USD);

    assertEquals(request.getBrand(), vehicle.getBrand());
    assertEquals(request.getModel(), vehicle.getModel());
    assertEquals(request.getYear(), vehicle.getYear());
    assertEquals(request.getColor(), vehicle.getColor());
    assertEquals(request.getLicensePlate(), vehicle.getLicensePlate());
    assertEquals(PATCH_PRICE_USD, vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
  }

  @Test
  void patchVehicle_WithPartialFields_ShouldUpdateOnlyProvidedFields() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehiclePatchRequestDTO request = VehiclePatchRequestDTOFactory.makeVehiclePatchRequestColorOnly();

    patchVehicle(vehicle, request, null);

    assertEquals("Toyota", vehicle.getBrand());
    assertEquals("Corolla", vehicle.getModel());
    assertEquals(2024, vehicle.getYear());
    assertEquals("Cinza", vehicle.getColor());
    assertEquals("ABC1D23", vehicle.getLicensePlate());
    assertEquals(new BigDecimal("25000.00"), vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
  }

  @Test
  void patchVehicle_WithPriceOnly_ShouldUpdateOnlyPrice() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehiclePatchRequestDTO request = VehiclePatchRequestDTOFactory.makeVehiclePatchRequestPriceOnly();

    patchVehicle(vehicle, request, PATCH_PRICE_USD);

    assertEquals("Toyota", vehicle.getBrand());
    assertEquals("Corolla", vehicle.getModel());
    assertEquals(2024, vehicle.getYear());
    assertEquals("Preto", vehicle.getColor());
    assertEquals("ABC1D23", vehicle.getLicensePlate());
    assertEquals(PATCH_PRICE_USD, vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
  }

  @Test
  void patchVehicle_WithBrandOnly_ShouldUpdateOnlyBrand() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehiclePatchRequestDTO request = VehiclePatchRequestDTO.builder()
        .brand("Honda")
        .build();

    patchVehicle(vehicle, request, null);

    assertEquals("Honda", vehicle.getBrand());
    assertEquals("Corolla", vehicle.getModel());
    assertEquals(2024, vehicle.getYear());
    assertEquals("Preto", vehicle.getColor());
    assertEquals("ABC1D23", vehicle.getLicensePlate());
    assertEquals(new BigDecimal("25000.00"), vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
  }

  @Test
  void patchVehicle_WithModelOnly_ShouldUpdateOnlyModel() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehiclePatchRequestDTO request = VehiclePatchRequestDTO.builder()
        .model("Civic")
        .build();

    patchVehicle(vehicle, request, null);

    assertEquals("Toyota", vehicle.getBrand());
    assertEquals("Civic", vehicle.getModel());
    assertEquals(2024, vehicle.getYear());
    assertEquals("Preto", vehicle.getColor());
    assertEquals("ABC1D23", vehicle.getLicensePlate());
    assertEquals(new BigDecimal("25000.00"), vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
  }

  @Test
  void patchVehicle_WithYearOnly_ShouldUpdateOnlyYear() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehiclePatchRequestDTO request = VehiclePatchRequestDTO.builder()
        .year(2025)
        .build();

    patchVehicle(vehicle, request, null);

    assertEquals("Toyota", vehicle.getBrand());
    assertEquals("Corolla", vehicle.getModel());
    assertEquals(2025, vehicle.getYear());
    assertEquals("Preto", vehicle.getColor());
    assertEquals("ABC1D23", vehicle.getLicensePlate());
    assertEquals(new BigDecimal("25000.00"), vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
  }

  @Test
  void patchVehicle_WithLicensePlateOnly_ShouldUpdateOnlyLicensePlate() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehiclePatchRequestDTO request = VehiclePatchRequestDTO.builder()
        .licensePlate("NEW1234")
        .build();

    patchVehicle(vehicle, request, null);

    assertEquals("Toyota", vehicle.getBrand());
    assertEquals("Corolla", vehicle.getModel());
    assertEquals(2024, vehicle.getYear());
    assertEquals("Preto", vehicle.getColor());
    assertEquals("NEW1234", vehicle.getLicensePlate());
    assertEquals(new BigDecimal("25000.00"), vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
  }

  @Test
  void patchVehicle_WithMultipleFields_ShouldUpdateOnlyProvidedFields() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehiclePatchRequestDTO request = VehiclePatchRequestDTO.builder()
        .brand("Ford")
        .model("Mustang")
        .color("Vermelho")
        .build();

    patchVehicle(vehicle, request, null);

    assertEquals("Ford", vehicle.getBrand());
    assertEquals("Mustang", vehicle.getModel());
    assertEquals(2024, vehicle.getYear());
    assertEquals("Vermelho", vehicle.getColor());
    assertEquals("ABC1D23", vehicle.getLicensePlate());
    assertEquals(new BigDecimal("25000.00"), vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
  }

  @Test
  void patchVehicle_WithNullRequestFields_ShouldKeepOriginalValues() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehiclePatchRequestDTO request = VehiclePatchRequestDTO.builder()
        .brand(null)
        .model(null)
        .year(null)
        .color(null)
        .licensePlate(null)
        .priceBrl(null)
        .build();

    patchVehicle(vehicle, request, null);

    assertEquals("Toyota", vehicle.getBrand());
    assertEquals("Corolla", vehicle.getModel());
    assertEquals(2024, vehicle.getYear());
    assertEquals("Preto", vehicle.getColor());
    assertEquals("ABC1D23", vehicle.getLicensePlate());
    assertEquals(new BigDecimal("25000.00"), vehicle.getPriceUsd());
    assertTrue(vehicle.getActive());
  }

  @Test
  void mapToVehicle_ShouldAlwaysSetActiveToTrue() {
    VehicleRequestDTO request = VehicleRequestDTOFactory.makeVehicleRequestDTO();

    Vehicle result1 = mapToVehicle(request, PRICE_USD);
    Vehicle result2 = mapToVehicle(request, UPDATED_PRICE_USD);

    assertTrue(result1.getActive());
    assertTrue(result2.getActive());
  }

  @Test
  void updateVehicle_ShouldKeepActiveStatus() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    vehicle.setActive(true);
    VehicleRequestDTO request = VehicleRequestDTOFactory.makeVehicleRequestDTOHonda();

    updateVehicle(vehicle, request, UPDATED_PRICE_USD);

    assertTrue(vehicle.getActive());

    vehicle.setActive(false);
    updateVehicle(vehicle, request, UPDATED_PRICE_USD);
    assertFalse(vehicle.getActive());
  }

  @Test
  void patchVehicle_ShouldKeepActiveStatus() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    vehicle.setActive(true);
    VehiclePatchRequestDTO request = VehiclePatchRequestDTOFactory.makeVehiclePatchRequestColorOnly();

    patchVehicle(vehicle, request, null);

    assertTrue(vehicle.getActive());

    vehicle.setActive(false);
    patchVehicle(vehicle, request, null);
    assertFalse(vehicle.getActive());
  }

  @Test
  void mapToVehicle_WithZeroPrice_ShouldReturnVehicleWithZeroPrice() {
    VehicleRequestDTO request = VehicleRequestDTOFactory.makeVehicleRequestDTO();
    BigDecimal zeroPrice = BigDecimal.ZERO;

    Vehicle result = mapToVehicle(request, zeroPrice);

    assertNotNull(result);
    assertEquals(zeroPrice, result.getPriceUsd());
    assertTrue(result.getActive());
  }

  @Test
  void updateVehicle_WithZeroPrice_ShouldUpdatePriceToZero() {
    Vehicle vehicle = VehicleFactory.makeVehicle();
    VehicleRequestDTO request = VehicleRequestDTOFactory.makeVehicleRequestDTOHonda();
    BigDecimal zeroPrice = BigDecimal.ZERO;

    updateVehicle(vehicle, request, zeroPrice);

    assertEquals(zeroPrice, vehicle.getPriceUsd());
  }
}