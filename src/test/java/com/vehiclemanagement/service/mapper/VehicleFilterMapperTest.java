package com.vehiclemanagement.service.mapper;

import com.vehiclemanagement.dto.request.VehicleFilterDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.vehiclemanagement.service.mapper.VehicleFilterMapper.mapToVehicleFilter;
import static org.junit.jupiter.api.Assertions.*;

class VehicleFilterMapperTest {

  @Test
  void mapToVehicleFilter_WithAllParameters_ShouldReturnVehicleFilterDTO() {
    String brand = "Toyota";
    Integer year = 2024;
    String color = "Preto";
    BigDecimal minPrice = new BigDecimal("100000.00");
    BigDecimal maxPrice = new BigDecimal("200000.00");

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertEquals(brand, result.getBrand());
    assertEquals(year, result.getYear());
    assertEquals(color, result.getColor());
    assertEquals(minPrice, result.getMinPrice());
    assertEquals(maxPrice, result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithOnlyBrand_ShouldReturnVehicleFilterDTO() {
    String brand = "Honda";
    Integer year = null;
    String color = null;
    BigDecimal minPrice = null;
    BigDecimal maxPrice = null;

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertEquals(brand, result.getBrand());
    assertNull(result.getYear());
    assertNull(result.getColor());
    assertNull(result.getMinPrice());
    assertNull(result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithOnlyYear_ShouldReturnVehicleFilterDTO() {
    String brand = null;
    Integer year = 2023;
    String color = null;
    BigDecimal minPrice = null;
    BigDecimal maxPrice = null;

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertNull(result.getBrand());
    assertEquals(year, result.getYear());
    assertNull(result.getColor());
    assertNull(result.getMinPrice());
    assertNull(result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithOnlyColor_ShouldReturnVehicleFilterDTO() {
    String brand = null;
    Integer year = null;
    String color = "Branco";
    BigDecimal minPrice = null;
    BigDecimal maxPrice = null;

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertNull(result.getBrand());
    assertNull(result.getYear());
    assertEquals(color, result.getColor());
    assertNull(result.getMinPrice());
    assertNull(result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithOnlyMinPrice_ShouldReturnVehicleFilterDTO() {
    String brand = null;
    Integer year = null;
    String color = null;
    BigDecimal minPrice = new BigDecimal("80000.00");
    BigDecimal maxPrice = null;

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertNull(result.getBrand());
    assertNull(result.getYear());
    assertNull(result.getColor());
    assertEquals(minPrice, result.getMinPrice());
    assertNull(result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithOnlyMaxPrice_ShouldReturnVehicleFilterDTO() {
    String brand = null;
    Integer year = null;
    String color = null;
    BigDecimal minPrice = null;
    BigDecimal maxPrice = new BigDecimal("150000.00");

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertNull(result.getBrand());
    assertNull(result.getYear());
    assertNull(result.getColor());
    assertNull(result.getMinPrice());
    assertEquals(maxPrice, result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithBrandAndYear_ShouldReturnVehicleFilterDTO() {
    String brand = "Ford";
    Integer year = 2022;
    String color = null;
    BigDecimal minPrice = null;
    BigDecimal maxPrice = null;

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertEquals(brand, result.getBrand());
    assertEquals(year, result.getYear());
    assertNull(result.getColor());
    assertNull(result.getMinPrice());
    assertNull(result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithPriceRange_ShouldReturnVehicleFilterDTO() {
    String brand = null;
    Integer year = null;
    String color = null;
    BigDecimal minPrice = new BigDecimal("90000.00");
    BigDecimal maxPrice = new BigDecimal("180000.00");

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertNull(result.getBrand());
    assertNull(result.getYear());
    assertNull(result.getColor());
    assertEquals(minPrice, result.getMinPrice());
    assertEquals(maxPrice, result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithBrandAndColorAndPrice_ShouldReturnVehicleFilterDTO() {
    String brand = "Volkswagen";
    Integer year = null;
    String color = "Azul";
    BigDecimal minPrice = new BigDecimal("200000.00");
    BigDecimal maxPrice = new BigDecimal("250000.00");

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertEquals(brand, result.getBrand());
    assertNull(result.getYear());
    assertEquals(color, result.getColor());
    assertEquals(minPrice, result.getMinPrice());
    assertEquals(maxPrice, result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithAllNullParameters_ShouldReturnVehicleFilterDTOWithNulls() {
    String brand = null;
    Integer year = null;
    String color = null;
    BigDecimal minPrice = null;
    BigDecimal maxPrice = null;

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertNull(result.getBrand());
    assertNull(result.getYear());
    assertNull(result.getColor());
    assertNull(result.getMinPrice());
    assertNull(result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithZeroPrices_ShouldReturnVehicleFilterDTO() {
    String brand = "Toyota";
    Integer year = 2024;
    String color = "Preto";
    BigDecimal minPrice = BigDecimal.ZERO;
    BigDecimal maxPrice = BigDecimal.ZERO;

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertEquals(brand, result.getBrand());
    assertEquals(year, result.getYear());
    assertEquals(color, result.getColor());
    assertEquals(BigDecimal.ZERO, result.getMinPrice());
    assertEquals(BigDecimal.ZERO, result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithNegativePrices_ShouldReturnVehicleFilterDTO() {
    String brand = null;
    Integer year = null;
    String color = null;
    BigDecimal minPrice = new BigDecimal("-10000.00");
    BigDecimal maxPrice = new BigDecimal("-5000.00");

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertNull(result.getBrand());
    assertNull(result.getYear());
    assertNull(result.getColor());
    assertEquals(minPrice, result.getMinPrice());
    assertEquals(maxPrice, result.getMaxPrice());
  }

  @Test
  void mapToVehicleFilter_WithDecimalPrices_ShouldReturnVehicleFilterDTO() {
    String brand = null;
    Integer year = null;
    String color = null;
    BigDecimal minPrice = new BigDecimal("75000.50");
    BigDecimal maxPrice = new BigDecimal("125000.75");

    VehicleFilterDTO result = mapToVehicleFilter(brand, year, color, minPrice, maxPrice);

    assertNotNull(result);
    assertNull(result.getBrand());
    assertNull(result.getYear());
    assertNull(result.getColor());
    assertEquals(minPrice, result.getMinPrice());
    assertEquals(maxPrice, result.getMaxPrice());
  }
}