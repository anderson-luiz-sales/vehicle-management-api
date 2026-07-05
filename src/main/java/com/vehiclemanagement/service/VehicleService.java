package com.vehiclemanagement.service;

import static com.vehiclemanagement.service.mapper.VehicleBrandReportMapper.mapToVehicleBrandReport;
import static com.vehiclemanagement.service.mapper.VehicleResponseMapper.mapToVehicleResponse;
import static com.vehiclemanagement.utils.ErrorLogsUtils.*;

import com.vehiclemanagement.dto.request.VehicleFilterDTO;
import com.vehiclemanagement.dto.request.VehicleRequestDTO;
import com.vehiclemanagement.dto.response.VehicleBrandReportResponseDTO;
import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.exception.VehicleServiceException;
import com.vehiclemanagement.repository.VehicleRepository;
import com.vehiclemanagement.service.mapper.VehicleMapper;
import com.vehiclemanagement.service.mapper.VehicleResponseMapper;
import com.vehiclemanagement.specification.VehicleSpecification;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {

  private final VehicleRepository vehicleRepository;
  private final ExchangeRateService exchangeRateService;

  public Page<VehicleResponseDTO> findAll(VehicleFilterDTO filter, Pageable pageable) {
    try {
      Page<Vehicle> vehicles = vehicleRepository.findAll(VehicleSpecification.filter(
          filter), pageable);

      return vehicles.map(VehicleResponseMapper::mapToVehicleResponse);
    } catch (Exception ex) {
      log.error(ERROR_MESSAGE, VEHICLE_LIST_ERROR, ex.getMessage(), ex);
      throw new VehicleServiceException(
          METHOD_FIND_ALL,
          "",
          VEHICLE_LIST_ERROR,
          HttpStatus.UNPROCESSABLE_ENTITY
      );
    }
  }

  public VehicleResponseDTO findById(Long id) {
    Vehicle vehicle = vehicleRepository.findByIdAndActiveTrue(id)
        .orElseThrow(() -> new VehicleServiceException(
            METHOD_FIND_BY_ID,
            String.valueOf(id),
            VEHICLE_NOT_FOUND,
            HttpStatus.NOT_FOUND
        ));

    return mapToVehicleResponse(vehicle);
  }

  public List<VehicleBrandReportResponseDTO> getVehiclesByBrand() {
    try {
      return mapToVehicleBrandReport(
          vehicleRepository.countVehiclesByBrand()
      );
    } catch (Exception ex) {
      log.error(ERROR_MESSAGE, VEHICLE_LIST_ERROR, ex.getMessage(), ex);
      throw new VehicleServiceException(
          METHOD_FIND_ALL,
          "",
          VEHICLE_LIST_ERROR,
          HttpStatus.UNPROCESSABLE_ENTITY
      );
    }
  }

  public VehicleResponseDTO create(VehicleRequestDTO request) {
    try {
      if (vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
        throw new VehicleServiceException(
            METHOD_CREATE,
            request.getLicensePlate(),
            VEHICLE_ALREADY_EXISTS,
            HttpStatus.CONFLICT
        );
      }

      BigDecimal priceUsd = exchangeRateService.convertBrlToUsd(
          request.getPriceBrl()
      );

      Vehicle vehicle = VehicleMapper.mapToVehicle(
          request,
          priceUsd
      );

      Vehicle savedVehicle = vehicleRepository.save(vehicle);

      return mapToVehicleResponse(savedVehicle);
    } catch (VehicleServiceException ex) {
      throw ex;
    } catch (Exception ex) {
      log.error(ERROR_MESSAGE, VEHICLE_CREATE_ERROR, ex.getMessage(), ex);
      throw new VehicleServiceException(
          METHOD_CREATE,
          "",
          VEHICLE_CREATE_ERROR,
          HttpStatus.UNPROCESSABLE_ENTITY
      );
    }
  }
}