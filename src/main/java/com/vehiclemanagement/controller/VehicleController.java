package com.vehiclemanagement.controller;

import static com.vehiclemanagement.service.mapper.VehicleFilterMapper.mapToVehicleFilter;

import com.vehiclemanagement.controller.swagger.VehicleControllerDoc;
import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import com.vehiclemanagement.exception.BadRequestMessageException;
import com.vehiclemanagement.service.VehicleService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vehicles")
@CrossOrigin(origins = "*")
@BadRequestMessageException("Bad request")
public class VehicleController implements VehicleControllerDoc {

  private final VehicleService vehicleService;

  @Override
  @GetMapping
  public ResponseEntity<Page<VehicleResponseDTO>> findAllVehicles(
      @RequestParam(required = false) String brand,
      @RequestParam(required = false) Integer year,
      @RequestParam(required = false) String color,
      @RequestParam(required = false) BigDecimal minPrice,
      @RequestParam(required = false) BigDecimal maxPrice,
      @ParameterObject Pageable pageable) {

    log.info("Listing all vehicles.");

    return ResponseEntity.ok(vehicleService.findAll(mapToVehicleFilter(
        brand,
        year,
        color,
        minPrice,
        maxPrice), pageable));
  }

  @GetMapping("/{id}")
  @Override
  public ResponseEntity<VehicleResponseDTO> findVehicleById(@PathVariable Long id) {
    log.info("Finding vehicle by id: {}", id);
    return ResponseEntity.ok(vehicleService.findById(id));
  }
}