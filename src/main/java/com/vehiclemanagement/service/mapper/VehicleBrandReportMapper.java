package com.vehiclemanagement.service.mapper;

import com.vehiclemanagement.dto.response.VehicleBrandReportResponseDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleBrandReportMapper {

  public static List<VehicleBrandReportResponseDTO> mapToVehicleBrandReport(
      List<Object[]> result) {

    return result.stream()
        .map(vehicle -> VehicleBrandReportResponseDTO.builder()
            .brand((String) vehicle[0])
            .quantity((Long) vehicle[1])
            .build())
        .collect(Collectors.toList());

  }

}