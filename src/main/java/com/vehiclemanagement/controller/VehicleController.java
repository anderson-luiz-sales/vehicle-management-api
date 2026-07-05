package com.vehiclemanagement.controller;

import static com.vehiclemanagement.service.mapper.VehicleFilterMapper.mapToVehicleFilter;

import com.vehiclemanagement.controller.swagger.VehicleControllerDoc;
import com.vehiclemanagement.dto.request.VehiclePatchRequestDTO;
import com.vehiclemanagement.dto.request.VehicleRequestDTO;
import com.vehiclemanagement.dto.response.VehicleBrandReportResponseDTO;
import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import com.vehiclemanagement.exception.BadRequestMessageException;
import com.vehiclemanagement.service.VehicleService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vehicles")
@CrossOrigin(origins = "*")
@BadRequestMessageException("Bad request")
public class VehicleController implements VehicleControllerDoc {

  private final VehicleService vehicleService;

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
  public ResponseEntity<VehicleResponseDTO> findVehicleById(@PathVariable Long id) {
    log.info("Finding vehicle by id: {}", id);
    return ResponseEntity.ok(vehicleService.findById(id));
  }

  @GetMapping("/reports/by-brand")
  public ResponseEntity<List<VehicleBrandReportResponseDTO>> getVehiclesByBrand() {
    log.info("Generating report grouped by brand.");
    return ResponseEntity.ok(vehicleService.getVehiclesByBrand());
  }

  @PostMapping
  public ResponseEntity<VehicleResponseDTO> createVehicle(
      @Valid @RequestBody VehicleRequestDTO request) {
    log.info("Creating vehicle.");
    return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.create(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<VehicleResponseDTO> updateVehicle(Long id, VehicleRequestDTO request) {
    return ResponseEntity.ok(vehicleService.update(id, request));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<VehicleResponseDTO> patchVehicle(
      @PathVariable Long id,
      @Valid @RequestBody VehiclePatchRequestDTO request
  ) {
    return ResponseEntity.ok(vehicleService.patch(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
    vehicleService.delete(id);
    return ResponseEntity.noContent().build();
  }
}