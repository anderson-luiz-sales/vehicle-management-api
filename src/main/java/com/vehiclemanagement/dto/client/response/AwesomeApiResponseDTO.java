package com.vehiclemanagement.dto.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AwesomeApiResponseDTO {

  @JsonProperty("USDBRL")
  private UsdBrlDTO usdbrl;

}