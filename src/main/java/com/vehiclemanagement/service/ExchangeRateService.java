package com.vehiclemanagement.service;

import com.vehiclemanagement.client.AwesomeApiClient;
import com.vehiclemanagement.client.FrankfurterApiClient;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

  private final AwesomeApiClient awesomeApiClient;

  private final FrankfurterApiClient frankfurterApiClient;

  public BigDecimal getDollarRate() {
    try {
      return new BigDecimal(
          awesomeApiClient
              .getDollarRate()
              .getUsdbrl()
              .getBid()
      );
    } catch (Exception ex) {
      log.warn("AwesomeAPI unavailable. Using Frankfurter fallback.");
      return frankfurterApiClient
          .getDollarRate()
          .getRates()
          .getBrl();
    }
  }

}