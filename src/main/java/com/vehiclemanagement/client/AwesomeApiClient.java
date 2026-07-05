package com.vehiclemanagement.client;

import com.vehiclemanagement.config.FeignConfiguration;
import com.vehiclemanagement.dto.client.response.AwesomeApiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "awesome-api",
    url = "${exchange.awesome.url}",
    configuration = FeignConfiguration.class
)
public interface AwesomeApiClient {

  @GetMapping("/json/last/USD-BRL")
  AwesomeApiResponseDTO getDollarRate();

}