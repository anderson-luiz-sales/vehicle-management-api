package com.vehiclemanagement.client;

import com.vehiclemanagement.config.FeignConfiguration;
import com.vehiclemanagement.dto.client.response.FrankfurterResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "frankfurter-api",
    url = "${exchange.frankfurter.url}",
    configuration = FeignConfiguration.class
)
public interface FrankfurterApiClient {

  @GetMapping("/latest?from=USD&to=BRL")
  FrankfurterResponseDTO getDollarRate();

}