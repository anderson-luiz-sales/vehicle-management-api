package com.vehiclemanagement.controller.swagger;

import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.math.BigDecimal;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface VehicleControllerDoc {

  @Operation(
      summary = "Lista todos os veículos",
      description = "Retorna uma lista paginada de veículos ativos. Permite filtrar por marca, ano, cor e faixa de preço."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Veículos encontrados com sucesso."),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
  })
  ResponseEntity<Page<VehicleResponseDTO>> findAllVehicles(
      @Parameter(description = "Marca", example = "Toyota")
      String brand,

      @Parameter(description = "Ano", example = "2024")
      Integer year,

      @Parameter(description = "Cor", example = "Preto")
      String color,

      @Parameter(description = "Preço mínimo", example = "20000")
      BigDecimal minPrice,

      @Parameter(description = "Preço máximo", example = "40000")
      BigDecimal maxPrice,

      @ParameterObject Pageable pageable
  );

}