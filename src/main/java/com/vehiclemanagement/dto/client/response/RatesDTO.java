package com.vehiclemanagement.dto.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RatesDTO {

  @JsonProperty("BRL")
  private BigDecimal brl;

}