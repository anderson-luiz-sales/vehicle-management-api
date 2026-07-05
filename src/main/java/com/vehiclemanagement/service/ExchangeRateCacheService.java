package com.vehiclemanagement.service;

import com.vehiclemanagement.client.AwesomeApiClient;
import com.vehiclemanagement.client.FrankfurterApiClient;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateCacheService {

  private final AwesomeApiClient awesomeApiClient;
  private final FrankfurterApiClient frankfurterApiClient;

  @Cacheable(value = "usd-rate", key = "'current'")
  public BigDecimal getDollarRate() {
    log.info("Buscando cotação na API...");

    try {
      return new BigDecimal(
          awesomeApiClient
              .getDollarRate()
              .getUsdbrl()
              .getBid()
      );
    } catch (Exception ex) {
      log.warn("AwesomeAPI indisponível. Utilizando Frankfurter.");
      return frankfurterApiClient
          .getDollarRate()
          .getRates()
          .getBrl();
    }
  }

}