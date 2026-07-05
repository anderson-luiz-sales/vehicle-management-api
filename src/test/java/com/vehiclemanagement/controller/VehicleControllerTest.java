package com.vehiclemanagement.controller;

import com.vehiclemanagement.dto.request.VehiclePatchRequestDTO;
import com.vehiclemanagement.dto.request.VehicleRequestDTO;
import com.vehiclemanagement.dto.response.VehicleBrandReportResponseDTO;
import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import com.vehiclemanagement.factory.VehicleBrandReportResponseDTOFactory;
import com.vehiclemanagement.factory.VehiclePatchRequestDTOFactory;
import com.vehiclemanagement.factory.VehicleRequestDTOFactory;
import com.vehiclemanagement.factory.VehicleResponseDTOFactory;
import com.vehiclemanagement.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.vehiclemanagement.service.mapper.VehicleFilterMapper.mapToVehicleFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

  private static final Long VEHICLE_ID = 1L;

  @InjectMocks
  private VehicleController vehicleController;

  @Mock
  private VehicleService vehicleService;

  private VehicleRequestDTO vehicleRequestDTO;
  private VehicleResponseDTO vehicleResponseDTO;
  private VehiclePatchRequestDTO vehiclePatchRequestDTO;
  private Pageable pageable;

  @BeforeEach
  void setUp() {
    vehicleRequestDTO = VehicleRequestDTOFactory.makeVehicleRequestDTO();
    vehicleResponseDTO = VehicleResponseDTOFactory.makeVehicleResponseDTO();
    vehiclePatchRequestDTO = VehiclePatchRequestDTOFactory.makeVehiclePatchRequestDTO();
    pageable = PageRequest.of(0, 10);
  }

  @Test
  void findAllVehicles_WithFilters_ShouldReturnPageOfVehicles() {
    String brand = "Toyota";
    Integer year = 2024;
    String color = "Preto";
    BigDecimal minPrice = new BigDecimal("100000.00");
    BigDecimal maxPrice = new BigDecimal("150000.00");

    Page<VehicleResponseDTO> expectedPage = new PageImpl<>(
        Collections.singletonList(vehicleResponseDTO));

    when(vehicleService.findAll(any(), eq(pageable))).thenReturn(expectedPage);

    ResponseEntity<Page<VehicleResponseDTO>> response = vehicleController.findAllVehicles(
        brand, year, color, minPrice, maxPrice, pageable);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().getContent().size());
    assertEquals(vehicleResponseDTO, response.getBody().getContent().get(0));

    verify(vehicleService, times(1)).findAll(
        mapToVehicleFilter(brand, year, color, minPrice, maxPrice),
        pageable
    );
  }

  @Test
  void findAllVehicles_WithoutFilters_ShouldReturnAllVehicles() {
    Page<VehicleResponseDTO> expectedPage = new PageImpl<>(
        Collections.singletonList(vehicleResponseDTO));

    when(vehicleService.findAll(any(), eq(pageable))).thenReturn(expectedPage);

    ResponseEntity<Page<VehicleResponseDTO>> response = vehicleController.findAllVehicles(
        null, null, null, null, null, pageable);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().getContent().size());

    verify(vehicleService, times(1)).findAll(
        mapToVehicleFilter(null, null, null, null, null),
        pageable
    );
  }

  @Test
  void findAllVehicles_WhenNoVehiclesFound_ShouldReturnEmptyPage() {
    Page<VehicleResponseDTO> emptyPage = new PageImpl<>(Collections.emptyList());
    when(vehicleService.findAll(any(), eq(pageable))).thenReturn(emptyPage);

    ResponseEntity<Page<VehicleResponseDTO>> response = vehicleController.findAllVehicles(
        null, null, null, null, null, pageable);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getContent().isEmpty());

    verify(vehicleService, times(1)).findAll(any(), eq(pageable));
  }

  @Test
  void findVehicleById_WithValidId_ShouldReturnVehicle() {
    when(vehicleService.findById(VEHICLE_ID)).thenReturn(vehicleResponseDTO);

    ResponseEntity<VehicleResponseDTO> response = vehicleController.findVehicleById(VEHICLE_ID);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(vehicleResponseDTO, response.getBody());
    assertEquals(VEHICLE_ID, response.getBody().getId());

    verify(vehicleService, times(1)).findById(VEHICLE_ID);
  }

  @Test
  void getVehiclesByBrand_ShouldReturnBrandReport() {
    List<VehicleBrandReportResponseDTO> expectedReport =
        VehicleBrandReportResponseDTOFactory.makeVehicleBrandReportList();
    when(vehicleService.getVehiclesByBrand()).thenReturn(expectedReport);

    ResponseEntity<List<VehicleBrandReportResponseDTO>> response =
        vehicleController.getVehiclesByBrand();

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(5, response.getBody().size());
    assertEquals("Toyota", response.getBody().get(0).getBrand());
    assertEquals(15L, response.getBody().get(0).getQuantity());

    verify(vehicleService, times(1)).getVehiclesByBrand();
  }

  @Test
  void getVehiclesByBrand_WhenNoVehiclesFound_ShouldReturnEmptyList() {
    when(vehicleService.getVehiclesByBrand()).thenReturn(Collections.emptyList());

    ResponseEntity<List<VehicleBrandReportResponseDTO>> response =
        vehicleController.getVehiclesByBrand();

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().isEmpty());

    verify(vehicleService, times(1)).getVehiclesByBrand();
  }

  @Test
  void createVehicle_WithValidData_ShouldReturnCreatedVehicle() {
    when(vehicleService.create(vehicleRequestDTO)).thenReturn(vehicleResponseDTO);

    ResponseEntity<VehicleResponseDTO> response =
        vehicleController.createVehicle(vehicleRequestDTO);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(vehicleResponseDTO, response.getBody());

    verify(vehicleService, times(1)).create(vehicleRequestDTO);
  }

  @Test
  void updateVehicle_WithValidData_ShouldReturnUpdatedVehicle() {
    when(vehicleService.update(eq(VEHICLE_ID), eq(vehicleRequestDTO)))
        .thenReturn(vehicleResponseDTO);

    ResponseEntity<VehicleResponseDTO> response =
        vehicleController.updateVehicle(VEHICLE_ID, vehicleRequestDTO);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(vehicleResponseDTO, response.getBody());

    verify(vehicleService, times(1)).update(VEHICLE_ID, vehicleRequestDTO);
  }

  @Test
  void patchVehicle_WithValidData_ShouldReturnPatchedVehicle() {
    VehicleResponseDTO patchedResponse = VehicleResponseDTOFactory.makeVehicleResponseDTOPatched();

    when(vehicleService.patch(eq(VEHICLE_ID), eq(vehiclePatchRequestDTO)))
        .thenReturn(patchedResponse);

    ResponseEntity<VehicleResponseDTO> response =
        vehicleController.patchVehicle(VEHICLE_ID, vehiclePatchRequestDTO);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Cinza", response.getBody().getColor());

    verify(vehicleService, times(1)).patch(VEHICLE_ID, vehiclePatchRequestDTO);
  }

  @Test
  void deleteVehicle_WithValidId_ShouldReturnNoContent() {
    doNothing().when(vehicleService).delete(VEHICLE_ID);

    ResponseEntity<Void> response = vehicleController.deleteVehicle(VEHICLE_ID);

    assertNotNull(response);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());

    verify(vehicleService, times(1)).delete(VEHICLE_ID);
  }

  @Test
  void patchVehicle_WithPriceOnlyUpdate_ShouldReturnPatchedVehicle() {
    VehiclePatchRequestDTO priceOnlyPatch =
        VehiclePatchRequestDTOFactory.makeVehiclePatchRequestPriceOnly();
    VehicleResponseDTO patchedResponse = VehicleResponseDTOFactory.makeVehicleResponseDTO();

    when(vehicleService.patch(eq(VEHICLE_ID), eq(priceOnlyPatch)))
        .thenReturn(patchedResponse);

    ResponseEntity<VehicleResponseDTO> response =
        vehicleController.patchVehicle(VEHICLE_ID, priceOnlyPatch);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    verify(vehicleService, times(1)).patch(VEHICLE_ID, priceOnlyPatch);
  }

  @Test
  void patchVehicle_WithColorOnlyUpdate_ShouldReturnPatchedVehicle() {
    VehiclePatchRequestDTO colorOnlyPatch =
        VehiclePatchRequestDTOFactory.makeVehiclePatchRequestColorOnly();
    VehicleResponseDTO patchedResponse = VehicleResponseDTOFactory.makeVehicleResponseDTOPatched();

    when(vehicleService.patch(eq(VEHICLE_ID), eq(colorOnlyPatch)))
        .thenReturn(patchedResponse);

    ResponseEntity<VehicleResponseDTO> response =
        vehicleController.patchVehicle(VEHICLE_ID, colorOnlyPatch);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    verify(vehicleService, times(1)).patch(VEHICLE_ID, colorOnlyPatch);
  }
}