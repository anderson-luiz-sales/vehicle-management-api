package com.vehiclemanagement.factory;

import com.vehiclemanagement.dto.response.VehicleBrandReportResponseDTO;
import java.util.Arrays;
import java.util.List;

public class VehicleBrandReportResponseDTOFactory {

  public static List<VehicleBrandReportResponseDTO> makeVehicleBrandReportList() {
    return Arrays.asList(
        VehicleBrandReportResponseDTO.builder()
            .brand("Toyota")
            .quantity(15L)
            .build(),
        VehicleBrandReportResponseDTO.builder()
            .brand("Honda")
            .quantity(12L)
            .build(),
        VehicleBrandReportResponseDTO.builder()
            .brand("Ford")
            .quantity(8L)
            .build(),
        VehicleBrandReportResponseDTO.builder()
            .brand("Volkswagen")
            .quantity(10L)
            .build(),
        VehicleBrandReportResponseDTO.builder()
            .brand("BMW")
            .quantity(5L)
            .build()
    );
  }
}