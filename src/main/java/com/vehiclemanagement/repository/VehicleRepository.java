package com.vehiclemanagement.repository;

import com.vehiclemanagement.entity.Vehicle;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>,
    JpaSpecificationExecutor<Vehicle> {

  Optional<Vehicle> findByIdAndActiveTrue(Long id);

}