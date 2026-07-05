package com.vehiclemanagement.service.mapper;

import com.vehiclemanagement.dto.response.VehicleBrandReportResponseDTO;
import com.vehiclemanagement.factory.VehicleBrandReportResponseDTOFactory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.vehiclemanagement.service.mapper.VehicleBrandReportMapper.mapToVehicleBrandReport;
import static org.junit.jupiter.api.Assertions.*;

class VehicleBrandReportMapperTest {

  @Test
  void mapToVehicleBrandReport_WithValidData_ShouldReturnListOfReports() {
    List<Object[]> result = Arrays.asList(
        new Object[]{"Toyota", 15L},
        new Object[]{"Honda", 12L},
        new Object[]{"Ford", 8L}
    );

    List<VehicleBrandReportResponseDTO> response = mapToVehicleBrandReport(result);

    assertNotNull(response);
    assertEquals(3, response.size());
    assertEquals("Toyota", response.get(0).getBrand());
    assertEquals(15L, response.get(0).getQuantity());
    assertEquals("Honda", response.get(1).getBrand());
    assertEquals(12L, response.get(1).getQuantity());
    assertEquals("Ford", response.get(2).getBrand());
    assertEquals(8L, response.get(2).getQuantity());
  }

  @Test
  void mapToVehicleBrandReport_WithSingleBrand_ShouldReturnListWithOneReport() {
    List<Object[]> result = Collections.singletonList(
        new Object[]{"Toyota", 15L}
    );

    List<VehicleBrandReportResponseDTO> response = mapToVehicleBrandReport(result);

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals("Toyota", response.get(0).getBrand());
    assertEquals(15L, response.get(0).getQuantity());
  }

  @Test
  void mapToVehicleBrandReport_WithEmptyList_ShouldReturnEmptyList() {
    List<Object[]> result = Collections.emptyList();

    List<VehicleBrandReportResponseDTO> response = mapToVehicleBrandReport(result);

    assertNotNull(response);
    assertTrue(response.isEmpty());
  }

  @Test
  void mapToVehicleBrandReport_WithNullList_ShouldThrowException() {
    assertThrows(NullPointerException.class, () -> mapToVehicleBrandReport(null));
  }

  @Test
  void mapToVehicleBrandReport_WithDifferentBrands_ShouldReturnCorrectMapping() {
    List<Object[]> result = Arrays.asList(
        new Object[]{"Volkswagen", 10L},
        new Object[]{"BMW", 5L},
        new Object[]{"Mercedes-Benz", 3L},
        new Object[]{"Chevrolet", 7L}
    );

    List<VehicleBrandReportResponseDTO> response = mapToVehicleBrandReport(result);

    assertNotNull(response);
    assertEquals(4, response.size());
    assertEquals("Volkswagen", response.get(0).getBrand());
    assertEquals(10L, response.get(0).getQuantity());
    assertEquals("BMW", response.get(1).getBrand());
    assertEquals(5L, response.get(1).getQuantity());
    assertEquals("Mercedes-Benz", response.get(2).getBrand());
    assertEquals(3L, response.get(2).getQuantity());
    assertEquals("Chevrolet", response.get(3).getBrand());
    assertEquals(7L, response.get(3).getQuantity());
  }

  @Test
  void mapToVehicleBrandReport_WithLargeQuantities_ShouldReturnCorrectMapping() {
    List<Object[]> result = Arrays.asList(
        new Object[]{"Toyota", 100L},
        new Object[]{"Honda", 85L},
        new Object[]{"Ford", 45L}
    );

    List<VehicleBrandReportResponseDTO> response = mapToVehicleBrandReport(result);

    assertNotNull(response);
    assertEquals(3, response.size());
    assertEquals("Toyota", response.get(0).getBrand());
    assertEquals(100L, response.get(0).getQuantity());
    assertEquals("Honda", response.get(1).getBrand());
    assertEquals(85L, response.get(1).getQuantity());
    assertEquals("Ford", response.get(2).getBrand());
    assertEquals(45L, response.get(2).getQuantity());
  }

  @Test
  void mapToVehicleBrandReport_WithZeroQuantity_ShouldReturnCorrectMapping() {
    List<Object[]> result = Collections.singletonList(
        new Object[]{"Tesla", 0L}
    );

    List<VehicleBrandReportResponseDTO> response = mapToVehicleBrandReport(result);

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals("Tesla", response.get(0).getBrand());
    assertEquals(0L, response.get(0).getQuantity());
  }

  @Test
  void mapToVehicleBrandReport_ShouldPreserveOrder() {
    List<Object[]> result = Arrays.asList(
        new Object[]{"A", 10L},
        new Object[]{"B", 20L},
        new Object[]{"C", 30L}
    );

    List<VehicleBrandReportResponseDTO> response = mapToVehicleBrandReport(result);

    assertNotNull(response);
    assertEquals(3, response.size());
    assertEquals("A", response.get(0).getBrand());
    assertEquals(10L, response.get(0).getQuantity());
    assertEquals("B", response.get(1).getBrand());
    assertEquals(20L, response.get(1).getQuantity());
    assertEquals("C", response.get(2).getBrand());
    assertEquals(30L, response.get(2).getQuantity());
  }
}