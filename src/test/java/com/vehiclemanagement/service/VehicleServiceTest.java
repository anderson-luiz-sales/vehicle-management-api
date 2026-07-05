package com.vehiclemanagement.service;

import com.vehiclemanagement.dto.request.VehicleFilterDTO;
import com.vehiclemanagement.dto.request.VehiclePatchRequestDTO;
import com.vehiclemanagement.dto.request.VehicleRequestDTO;
import com.vehiclemanagement.dto.response.VehicleBrandReportResponseDTO;
import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.exception.VehicleServiceException;
import com.vehiclemanagement.factory.VehicleFactory;
import com.vehiclemanagement.factory.VehicleFilterDTOFactory;
import com.vehiclemanagement.factory.VehiclePatchRequestDTOFactory;
import com.vehiclemanagement.factory.VehicleRequestDTOFactory;
import com.vehiclemanagement.factory.VehicleResponseDTOFactory;
import com.vehiclemanagement.repository.VehicleRepository;
import com.vehiclemanagement.specification.VehicleSpecification;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.vehiclemanagement.utils.ErrorLogsUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

  private static final Long VEHICLE_ID = 1L;

  @InjectMocks
  private VehicleService vehicleService;

  @Mock
  private VehicleRepository vehicleRepository;

  @Mock
  private ExchangeRateService exchangeRateService;

  private VehicleRequestDTO vehicleRequestDTO;
  private VehicleRequestDTO vehicleRequestDTOHonda;
  private VehiclePatchRequestDTO vehiclePatchRequestDTO;
  private VehicleFilterDTO vehicleFilterDTO;
  private Vehicle vehicle;
  private Vehicle vehicleHonda;
  private Pageable pageable;

  @BeforeEach
  void setUp() {
    vehicleRequestDTO = VehicleRequestDTOFactory.makeVehicleRequestDTO();
    vehicleRequestDTOHonda = VehicleRequestDTOFactory.makeVehicleRequestDTOHonda();
    vehiclePatchRequestDTO = VehiclePatchRequestDTOFactory.makeVehiclePatchRequestDTO();
    vehicleFilterDTO = VehicleFilterDTOFactory.makeVehicleFilterDTO();
    pageable = PageRequest.of(0, 10);

    vehicle = VehicleFactory.makeVehicle();
    vehicleHonda = VehicleFactory.makeVehicleHonda();
  }

  @Test
  void findAll_WithFilters_ShouldReturnPageOfVehicles() {
    Page<Vehicle> vehiclePage = new PageImpl<>(Collections.singletonList(vehicle));
    when(vehicleRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(vehiclePage);

    Page<VehicleResponseDTO> result = vehicleService.findAll(vehicleFilterDTO, pageable);

    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    assertEquals(vehicle.getBrand(), result.getContent().get(0).getBrand());
    assertEquals(vehicle.getModel(), result.getContent().get(0).getModel());
    verify(vehicleRepository, times(1)).findAll(any(Specification.class), eq(pageable));
  }

  @Test
  void findAll_WithoutFilters_ShouldReturnAllVehicles() {
    VehicleFilterDTO emptyFilter = new VehicleFilterDTO();
    Page<Vehicle> vehiclePage = new PageImpl<>(Arrays.asList(vehicle, vehicleHonda));
    when(vehicleRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(vehiclePage);

    Page<VehicleResponseDTO> result = vehicleService.findAll(emptyFilter, pageable);

    assertNotNull(result);
    assertEquals(2, result.getContent().size());
    assertEquals(vehicle.getBrand(), result.getContent().get(0).getBrand());
    assertEquals(vehicleHonda.getBrand(), result.getContent().get(1).getBrand());
    verify(vehicleRepository, times(1)).findAll(any(Specification.class), eq(pageable));
  }

  @Test
  void findAll_WhenNoVehiclesFound_ShouldReturnEmptyPage() {
    Page<Vehicle> emptyPage = new PageImpl<>(Collections.emptyList());
    when(vehicleRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(emptyPage);

    Page<VehicleResponseDTO> result = vehicleService.findAll(vehicleFilterDTO, pageable);

    assertNotNull(result);
    assertTrue(result.getContent().isEmpty());
    verify(vehicleRepository, times(1)).findAll(any(Specification.class), eq(pageable));
  }

  @Test
  void findAll_WhenRepositoryFails_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.findAll(any(Specification.class), eq(pageable)))
        .thenThrow(new RuntimeException("Database error"));

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.findAll(vehicleFilterDTO, pageable));

    assertEquals(VEHICLE_LIST_ERROR, exception.getMessage());
    assertEquals(METHOD_FIND_ALL, exception.getField());
    assertEquals("", exception.getValue());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).findAll(any(Specification.class), eq(pageable));
  }

  @Test
  void findById_WithValidId_ShouldReturnVehicle() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.of(vehicle));

    VehicleResponseDTO result = vehicleService.findById(VEHICLE_ID);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    assertEquals(vehicle.getYear(), result.getYear());
    assertEquals(vehicle.getColor(), result.getColor());
    assertEquals(vehicle.getLicensePlate(), result.getLicensePlate());
    assertEquals(vehicle.getPriceUsd(), result.getPriceUsd());
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
  }

  @Test
  void findById_WithInvalidId_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.empty());

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.findById(VEHICLE_ID));

    assertEquals(VEHICLE_NOT_FOUND, exception.getMessage());
    assertEquals(METHOD_FIND_BY_ID, exception.getField());
    assertEquals(String.valueOf(VEHICLE_ID), exception.getValue());
    assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
  }

  @Test
  void getVehiclesByBrand_ShouldReturnBrandReport() {
    List<Object[]> brandCounts = Arrays.asList(
        new Object[]{"Toyota", 15L},
        new Object[]{"Honda", 12L}
    );
    when(vehicleRepository.countVehiclesByBrand()).thenReturn(brandCounts);

    List<VehicleBrandReportResponseDTO> result = vehicleService.getVehiclesByBrand();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Toyota", result.get(0).getBrand());
    assertEquals(15L, result.get(0).getQuantity());
    verify(vehicleRepository, times(1)).countVehiclesByBrand();
  }

  @Test
  void getVehiclesByBrand_WhenNoVehiclesFound_ShouldReturnEmptyList() {
    when(vehicleRepository.countVehiclesByBrand()).thenReturn(Collections.emptyList());

    List<VehicleBrandReportResponseDTO> result = vehicleService.getVehiclesByBrand();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(vehicleRepository, times(1)).countVehiclesByBrand();
  }

  @Test
  void getVehiclesByBrand_WhenRepositoryFails_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.countVehiclesByBrand()).thenThrow(new RuntimeException("Database error"));

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.getVehiclesByBrand());

    assertEquals(VEHICLE_LIST_ERROR, exception.getMessage());
    assertEquals(METHOD_FIND_ALL, exception.getField());
    assertEquals("", exception.getValue());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).countVehiclesByBrand();
  }

  @Test
  void create_WithValidData_ShouldReturnCreatedVehicle() {
    when(vehicleRepository.existsByLicensePlate(vehicleRequestDTO.getLicensePlate()))
        .thenReturn(false);
    when(exchangeRateService.convertBrlToUsd(vehicleRequestDTO.getPriceBrl()))
        .thenReturn(vehicle.getPriceUsd());
    when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

    VehicleResponseDTO result = vehicleService.create(vehicleRequestDTO);

    assertNotNull(result);
    assertEquals(vehicle.getId(), result.getId());
    assertEquals(vehicle.getBrand(), result.getBrand());
    assertEquals(vehicle.getModel(), result.getModel());
    verify(vehicleRepository, times(1)).existsByLicensePlate(vehicleRequestDTO.getLicensePlate());
    verify(exchangeRateService, times(1)).convertBrlToUsd(vehicleRequestDTO.getPriceBrl());
    verify(vehicleRepository, times(1)).save(any(Vehicle.class));
  }

  @Test
  void create_WithExistingLicensePlate_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.existsByLicensePlate(vehicleRequestDTO.getLicensePlate()))
        .thenReturn(true);

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.create(vehicleRequestDTO));

    assertEquals(VEHICLE_ALREADY_EXISTS, exception.getMessage());
    assertEquals(METHOD_CREATE, exception.getField());
    assertEquals(vehicleRequestDTO.getLicensePlate(), exception.getValue());
    assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).existsByLicensePlate(vehicleRequestDTO.getLicensePlate());
    verify(exchangeRateService, never()).convertBrlToUsd(any(BigDecimal.class));
    verify(vehicleRepository, never()).save(any(Vehicle.class));
  }

  @Test
  void create_WhenExchangeRateFails_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.existsByLicensePlate(vehicleRequestDTO.getLicensePlate()))
        .thenReturn(false);
    when(exchangeRateService.convertBrlToUsd(vehicleRequestDTO.getPriceBrl()))
        .thenThrow(new RuntimeException("Exchange rate error"));

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.create(vehicleRequestDTO));

    assertEquals(VEHICLE_CREATE_ERROR, exception.getMessage());
    assertEquals(METHOD_CREATE, exception.getField());
    assertEquals("", exception.getValue());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).existsByLicensePlate(vehicleRequestDTO.getLicensePlate());
    verify(exchangeRateService, times(1)).convertBrlToUsd(vehicleRequestDTO.getPriceBrl());
    verify(vehicleRepository, never()).save(any(Vehicle.class));
  }

  @Test
  void create_WhenRepositoryFails_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.existsByLicensePlate(vehicleRequestDTO.getLicensePlate()))
        .thenReturn(false);
    when(exchangeRateService.convertBrlToUsd(vehicleRequestDTO.getPriceBrl()))
        .thenReturn(vehicle.getPriceUsd());
    when(vehicleRepository.save(any(Vehicle.class)))
        .thenThrow(new RuntimeException("Database error"));

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.create(vehicleRequestDTO));

    assertEquals(VEHICLE_CREATE_ERROR, exception.getMessage());
    assertEquals(METHOD_CREATE, exception.getField());
    assertEquals("", exception.getValue());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).existsByLicensePlate(vehicleRequestDTO.getLicensePlate());
    verify(exchangeRateService, times(1)).convertBrlToUsd(vehicleRequestDTO.getPriceBrl());
    verify(vehicleRepository, times(1)).save(any(Vehicle.class));
  }

  @Test
  void update_WithValidData_ShouldReturnUpdatedVehicle() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
    when(vehicleRepository.existsByLicensePlateAndIdNot(
        vehicleRequestDTOHonda.getLicensePlate(), VEHICLE_ID))
        .thenReturn(false);
    when(exchangeRateService.convertBrlToUsd(vehicleRequestDTOHonda.getPriceBrl()))
        .thenReturn(vehicleHonda.getPriceUsd());
    when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicleHonda);

    VehicleResponseDTO result = vehicleService.update(VEHICLE_ID, vehicleRequestDTOHonda);

    assertNotNull(result);
    assertEquals(vehicleHonda.getId(), result.getId());
    assertEquals(vehicleHonda.getBrand(), result.getBrand());
    assertEquals(vehicleHonda.getModel(), result.getModel());
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(vehicleRepository, times(1)).existsByLicensePlateAndIdNot(
        vehicleRequestDTOHonda.getLicensePlate(), VEHICLE_ID);
    verify(exchangeRateService, times(1)).convertBrlToUsd(vehicleRequestDTOHonda.getPriceBrl());
    verify(vehicleRepository, times(1)).save(any(Vehicle.class));
  }

  @Test
  void update_WithNonExistentVehicle_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.empty());

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.update(VEHICLE_ID, vehicleRequestDTO));

    assertEquals(VEHICLE_NOT_FOUND, exception.getMessage());
    assertEquals(METHOD_UPDATE, exception.getField());
    assertEquals(String.valueOf(VEHICLE_ID), exception.getValue());
    assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(vehicleRepository, never()).existsByLicensePlateAndIdNot(anyString(), anyLong());
    verify(exchangeRateService, never()).convertBrlToUsd(any(BigDecimal.class));
    verify(vehicleRepository, never()).save(any(Vehicle.class));
  }

  @Test
  void update_WithExistingLicensePlate_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
    when(vehicleRepository.existsByLicensePlateAndIdNot(
        vehicleRequestDTOHonda.getLicensePlate(), VEHICLE_ID))
        .thenReturn(true);

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.update(VEHICLE_ID, vehicleRequestDTOHonda));

    assertEquals(VEHICLE_ALREADY_EXISTS, exception.getMessage());
    assertEquals(METHOD_UPDATE, exception.getField());
    assertEquals(vehicleRequestDTOHonda.getLicensePlate(), exception.getValue());
    assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(vehicleRepository, times(1)).existsByLicensePlateAndIdNot(
        vehicleRequestDTOHonda.getLicensePlate(), VEHICLE_ID);
    verify(exchangeRateService, never()).convertBrlToUsd(any(BigDecimal.class));
    verify(vehicleRepository, never()).save(any(Vehicle.class));
  }

  @Test
  void patch_WithValidData_ShouldReturnPatchedVehicle() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
    when(vehicleRepository.existsByLicensePlateAndIdNot(
        vehiclePatchRequestDTO.getLicensePlate(), VEHICLE_ID))
        .thenReturn(false);
    when(exchangeRateService.convertBrlToUsd(vehiclePatchRequestDTO.getPriceBrl()))
        .thenReturn(new BigDecimal("24500.00"));
    when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

    VehicleResponseDTO result = vehicleService.patch(VEHICLE_ID, vehiclePatchRequestDTO);

    assertNotNull(result);
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(vehicleRepository, times(1)).existsByLicensePlateAndIdNot(
        vehiclePatchRequestDTO.getLicensePlate(), VEHICLE_ID);
    verify(exchangeRateService, times(1)).convertBrlToUsd(vehiclePatchRequestDTO.getPriceBrl());
    verify(vehicleRepository, times(1)).save(any(Vehicle.class));
  }

  @Test
  void patch_WithNonExistentVehicle_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.empty());

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.patch(VEHICLE_ID, vehiclePatchRequestDTO));

    assertEquals(VEHICLE_NOT_FOUND, exception.getMessage());
    assertEquals(METHOD_PATCH, exception.getField());
    assertEquals(String.valueOf(VEHICLE_ID), exception.getValue());
    assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(vehicleRepository, never()).save(any(Vehicle.class));
  }

  @Test
  void patch_WithPriceOnly_ShouldUpdateOnlyPrice() {
    VehiclePatchRequestDTO priceOnlyPatch = VehiclePatchRequestDTOFactory
        .makeVehiclePatchRequestPriceOnly();
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
    when(exchangeRateService.convertBrlToUsd(priceOnlyPatch.getPriceBrl()))
        .thenReturn(new BigDecimal("26000.00"));
    when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

    VehicleResponseDTO result = vehicleService.patch(VEHICLE_ID, priceOnlyPatch);

    assertNotNull(result);
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(exchangeRateService, times(1)).convertBrlToUsd(priceOnlyPatch.getPriceBrl());
    verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    verify(vehicleRepository, never()).existsByLicensePlateAndIdNot(anyString(), anyLong());
  }

  @Test
  void patch_WithColorOnly_ShouldUpdateOnlyColor() {
    VehiclePatchRequestDTO colorOnlyPatch = VehiclePatchRequestDTOFactory
        .makeVehiclePatchRequestColorOnly();
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
    when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

    VehicleResponseDTO result = vehicleService.patch(VEHICLE_ID, colorOnlyPatch);

    assertNotNull(result);
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(exchangeRateService, never()).convertBrlToUsd(any(BigDecimal.class));
    verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    verify(vehicleRepository, never()).existsByLicensePlateAndIdNot(anyString(), anyLong());
  }

  @Test
  void patch_WithExistingLicensePlate_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
    when(vehicleRepository.existsByLicensePlateAndIdNot(
        vehiclePatchRequestDTO.getLicensePlate(), VEHICLE_ID))
        .thenReturn(true);

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.patch(VEHICLE_ID, vehiclePatchRequestDTO));

    assertEquals(VEHICLE_ALREADY_EXISTS, exception.getMessage());
    assertEquals(METHOD_PATCH, exception.getField());
    assertEquals(vehiclePatchRequestDTO.getLicensePlate(), exception.getValue());
    assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(vehicleRepository, times(1)).existsByLicensePlateAndIdNot(
        vehiclePatchRequestDTO.getLicensePlate(), VEHICLE_ID);
    verify(exchangeRateService, never()).convertBrlToUsd(any(BigDecimal.class));
    verify(vehicleRepository, never()).save(any(Vehicle.class));
  }

  @Test
  void delete_WithValidId_ShouldDeactivateVehicle() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
    when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

    vehicleService.delete(VEHICLE_ID);

    assertFalse(vehicle.getActive());
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(vehicleRepository, times(1)).save(vehicle);
  }

  @Test
  void delete_WithNonExistentVehicle_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.empty());

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.delete(VEHICLE_ID));

    assertEquals(VEHICLE_NOT_FOUND, exception.getMessage());
    assertEquals(METHOD_DELETE, exception.getField());
    assertEquals(String.valueOf(VEHICLE_ID), exception.getValue());
    assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(vehicleRepository, never()).save(any(Vehicle.class));
  }

  @Test
  void delete_WhenRepositoryFails_ShouldThrowVehicleServiceException() {
    when(vehicleRepository.findByIdAndActiveTrue(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
    when(vehicleRepository.save(any(Vehicle.class)))
        .thenThrow(new RuntimeException("Database error"));

    VehicleServiceException exception = assertThrows(VehicleServiceException.class,
        () -> vehicleService.delete(VEHICLE_ID));

    assertEquals(VEHICLE_DELETE_ERROR, exception.getMessage());
    assertEquals(METHOD_DELETE, exception.getField());
    assertEquals(String.valueOf(VEHICLE_ID), exception.getValue());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getHttpStatus());
    verify(vehicleRepository, times(1)).findByIdAndActiveTrue(VEHICLE_ID);
    verify(vehicleRepository, times(1)).save(any(Vehicle.class));
  }
}