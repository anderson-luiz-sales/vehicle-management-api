package com.vehiclemanagement.repository;

import com.vehiclemanagement.entity.Vehicle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>,
    JpaSpecificationExecutor<Vehicle> {

  Optional<Vehicle> findByIdAndActiveTrue(Long id);

  @Query("""
      SELECT
          v.brand,
          COUNT(v)
      FROM Vehicle v
      WHERE v.active = true
      GROUP BY v.brand
      ORDER BY COUNT(v) DESC
      """)
  List<Object[]> countVehiclesByBrand();

}