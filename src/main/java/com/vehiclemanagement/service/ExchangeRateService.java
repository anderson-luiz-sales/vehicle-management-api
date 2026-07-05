package com.vehiclemanagement.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

  private final ExchangeRateCacheService exchangeRateCacheService;

  public BigDecimal convertBrlToUsd(BigDecimal priceBrl) {
    BigDecimal dollarRate = exchangeRateCacheService.getDollarRate();

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