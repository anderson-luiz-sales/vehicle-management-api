package com.vehiclemanagement.service;

import com.vehiclemanagement.client.AwesomeApiClient;
import com.vehiclemanagement.client.FrankfurterApiClient;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
      log.warn("AwesomeAPI indisponível. Utilizando Frankfurter.");
      return frankfurterApiClient
          .getDollarRate()
          .getRates()
          .getBrl();
    }
  }

  public BigDecimal convertBrlToUsd(BigDecimal priceBrl) {
    BigDecimal dollarRate = getDollarRate();
    if (dollarRate == null || BigDecimal.ZERO.compareTo(dollarRate) == 0) {
      throw new IllegalStateException("Não foi possível obter a cotação do dólar.");
    }
    return priceBrl.divide(
        dollarRate,
        2,
        RoundingMode.HALF_UP
    );
  }

}