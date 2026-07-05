package com.vehiclemanagement.controller.swagger;

import com.vehiclemanagement.dto.request.VehiclePatchRequestDTO;
import com.vehiclemanagement.dto.request.VehicleRequestDTO;
import com.vehiclemanagement.dto.response.VehicleBrandReportResponseDTO;
import com.vehiclemanagement.dto.response.VehicleResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

  @Operation(
      summary = "Busca um veículo por ID",
      description = "Retorna os detalhes de um veículo pelo seu identificador."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Veículo encontrado com sucesso."),
      @ApiResponse(responseCode = "404", description = "Veículo não encontrado.")
  })
  @GetMapping("/{id}")
  ResponseEntity<VehicleResponseDTO> findVehicleById(
      @Parameter(description = "ID do veículo", example = "1")
      @PathVariable Long id
  );


  @GetMapping("/reports/by-brand")
  @Operation(
      summary = "Relatório de veículos por marca",
      description = "Retorna a quantidade de veículos agrupados por marca."
  )
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso.")})
  ResponseEntity<List<VehicleBrandReportResponseDTO>> getVehiclesByBrand();

  @PostMapping
  @Operation(
      summary = "Cadastrar veículo",
      description = "Cadastra um novo veículo."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Veículo cadastrado."),
      @ApiResponse(responseCode = "409", description = "Placa já cadastrada.")
  })
  ResponseEntity<VehicleResponseDTO> createVehicle(@Valid @RequestBody VehicleRequestDTO request);

  @PutMapping("/{id}")
  @Operation(
      summary = "Atualizar veículo",
      description = "Atualiza completamente os dados de um veículo existente."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso."),
      @ApiResponse(responseCode = "400", description = "Dados inválidos."),
      @ApiResponse(responseCode = "404", description = "Veículo não encontrado."),
      @ApiResponse(responseCode = "409", description = "Placa já cadastrada.")
  })
  ResponseEntity<VehicleResponseDTO> updateVehicle(
      @Parameter(description = "ID do veículo", example = "1")
      @PathVariable Long id,

      @Valid
      @RequestBody
      VehicleRequestDTO request
  );

  @PatchMapping("/{id}")
  @Operation(
      summary = "Atualizar parcialmente um veículo",
      description = "Atualiza apenas os campos informados."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso."),
      @ApiResponse(responseCode = "404", description = "Veículo não encontrado."),
      @ApiResponse(responseCode = "409", description = "Placa já cadastrada.")
  })
  ResponseEntity<VehicleResponseDTO> patchVehicle(
      @Parameter(description = "ID do veículo", example = "1")
      @PathVariable Long id,

      @Valid
      @RequestBody
      VehiclePatchRequestDTO request
  );

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Remover veículo",
      description = "Realiza a remoção lógica (soft delete) de um veículo."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Veículo removido com sucesso."),
      @ApiResponse(responseCode = "404", description = "Veículo não encontrado.")
  })
  ResponseEntity<Void> deleteVehicle(
      @Parameter(description = "ID do veículo", example = "1")
      @PathVariable Long id
  );

}