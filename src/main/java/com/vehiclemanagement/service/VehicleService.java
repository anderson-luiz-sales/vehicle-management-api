package com.vehiclemanagement.service;

import com.vehiclemanagement.dto.request.VehicleFilterDTO;
import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import com.vehiclemanagement.entity.Vehicle;
import com.vehiclemanagement.exception.VehicleServiceException;
import com.vehiclemanagement.repository.VehicleRepository;
import com.vehiclemanagement.service.mapper.VehicleResponseMapper;
import com.vehiclemanagement.specification.VehicleSpecification;
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

  private static final String ERROR_MESSAGE = "{}: {}";
  private static final String METHOD_FIND_ALL = "findAllVehicles";
  private static final String VEHICLE_LIST_ERROR = "Erro ao listar veículos.";

  private final VehicleRepository vehicleRepository;

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
}