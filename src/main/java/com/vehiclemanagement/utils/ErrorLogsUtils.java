package com.vehiclemanagement.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorLogsUtils {

  public static final String ERROR_MESSAGE = "{}: {}";

  public static final String METHOD_FIND_ALL = "findAll";
  public static final String METHOD_FIND_BY_ID = "findById";
  public static final String METHOD_CREATE = "create";
  public static final String METHOD_UPDATE = "update";
  public static final String METHOD_PATCH = "patch";
  public static final String METHOD_DELETE = "delete";

  public static final String VEHICLE_NOT_FOUND = "Veículo não encontrado.";
  public static final String VEHICLE_LIST_ERROR = "Erro ao listar veículos.";
  public static final String VEHICLE_CREATE_ERROR = "Erro ao cadastrar veículo.";
  public static final String VEHICLE_UPDATE_ERROR = "Erro ao atualizar veículo.";
  public static final String VEHICLE_DELETE_ERROR = "Erro ao remover veículo.";
  public static final String VEHICLE_ALREADY_EXISTS = "Já existe um veículo com esta placa.";
  public static final String VEHICLE_PATCH_ERROR = "Erro ao atualizar parcialmente o veículo.";

}