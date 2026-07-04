package com.vehiclemanagement.specification;

import com.vehiclemanagement.dto.request.VehicleFilterDTO;
import com.vehiclemanagement.entity.Vehicle;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification {

  public static Specification<Vehicle> filter(VehicleFilterDTO filter) {

    return (root, query, builder) -> {
      var predicate = builder.conjunction();

      predicate = builder.and(predicate, builder.isTrue(root.get("active")));

      if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
        predicate = builder.and(predicate, builder.equal(root.get("brand"), filter.getBrand()));
      }

      if (filter.getYear() != null) {
        predicate = builder.and(predicate, builder.equal(root.get("year"), filter.getYear()));
      }

      if (filter.getColor() != null && !filter.getColor().isBlank()) {
        predicate = builder.and(predicate, builder.equal(root.get("color"), filter.getColor()));
      }

      if (filter.getMinPrice() != null) {
        predicate = builder.and(predicate, builder.greaterThanOrEqualTo(
            root.get("priceUsd"),
            filter.getMinPrice()));
      }

      if (filter.getMaxPrice() != null) {
        predicate = builder.and(predicate, builder.lessThanOrEqualTo(
            root.get("priceUsd"),
            filter.getMaxPrice()));
      }
      return predicate;
    };
  }
}