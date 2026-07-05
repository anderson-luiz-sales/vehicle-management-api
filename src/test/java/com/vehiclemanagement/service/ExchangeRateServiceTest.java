package com.vehiclemanagement.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

  @InjectMocks
  private ExchangeRateService exchangeRateService;

  @Mock
  private ExchangeRateCacheService exchangeRateCacheService;

  private BigDecimal priceBrl;
  private BigDecimal dollarRate;

  @BeforeEach
  void setUp() {
    priceBrl = new BigDecimal("145000.00");
    dollarRate = new BigDecimal("5.80");
  }

  @Test
  void convertBrlToUsd_WithValidRate_ShouldReturnConvertedValue() {
    when(exchangeRateCacheService.getDollarRate()).thenReturn(dollarRate);

    BigDecimal result = exchangeRateService.convertBrlToUsd(priceBrl);

    assertNotNull(result);
    assertEquals(new BigDecimal("25000.00"), result);
    assertEquals(2, result.scale());
    verify(exchangeRateCacheService, times(1)).getDollarRate();
  }

  @Test
  void convertBrlToUsd_WithDifferentRate_ShouldReturnCorrectConvertedValue() {
    BigDecimal differentPrice = new BigDecimal("100000.00");
    BigDecimal differentRate = new BigDecimal("5.00");

    when(exchangeRateCacheService.getDollarRate()).thenReturn(differentRate);

    BigDecimal result = exchangeRateService.convertBrlToUsd(differentPrice);

    assertNotNull(result);
    assertEquals(new BigDecimal("20000.00"), result);
    verify(exchangeRateCacheService, times(1)).getDollarRate();
  }

  @Test
  void convertBrlToUsd_WithSmallAmount_ShouldReturnCorrectConvertedValue() {
    BigDecimal smallPrice = new BigDecimal("1000.00");
    BigDecimal rate = new BigDecimal("5.80");

    when(exchangeRateCacheService.getDollarRate()).thenReturn(rate);

    BigDecimal result = exchangeRateService.convertBrlToUsd(smallPrice);

    assertNotNull(result);
    assertEquals(new BigDecimal("172.41"), result);
    verify(exchangeRateCacheService, times(1)).getDollarRate();
  }

  @Test
  void convertBrlToUsd_WithLargeAmount_ShouldReturnCorrectConvertedValue() {
    BigDecimal largePrice = new BigDecimal("1000000.00");
    BigDecimal rate = new BigDecimal("5.80");

    when(exchangeRateCacheService.getDollarRate()).thenReturn(rate);

    BigDecimal result = exchangeRateService.convertBrlToUsd(largePrice);

    assertNotNull(result);
    assertEquals(new BigDecimal("172413.79"), result);
    verify(exchangeRateCacheService, times(1)).getDollarRate();
  }

  @Test
  void convertBrlToUsd_WhenDollarRateIsNull_ShouldThrowIllegalStateException() {
    when(exchangeRateCacheService.getDollarRate()).thenReturn(null);

    IllegalStateException exception = assertThrows(IllegalStateException.class,
        () -> exchangeRateService.convertBrlToUsd(priceBrl));

    assertEquals("Não foi possível obter a cotação do dólar.", exception.getMessage());
    verify(exchangeRateCacheService, times(1)).getDollarRate();
  }

  @Test
  void convertBrlToUsd_WhenDollarRateIsZero_ShouldThrowIllegalStateException() {
    when(exchangeRateCacheService.getDollarRate()).thenReturn(BigDecimal.ZERO);

    IllegalStateException exception = assertThrows(IllegalStateException.class,
        () -> exchangeRateService.convertBrlToUsd(priceBrl));

    assertEquals("Não foi possível obter a cotação do dólar.", exception.getMessage());
    verify(exchangeRateCacheService, times(1)).getDollarRate();
  }

  @Test
  void convertBrlToUsd_WhenDollarRateIsNegative_ShouldReturnNegativeValue() {
    BigDecimal negativeRate = new BigDecimal("-5.80");
    when(exchangeRateCacheService.getDollarRate()).thenReturn(negativeRate);

    BigDecimal result = exchangeRateService.convertBrlToUsd(priceBrl);

    assertNotNull(result);
    assertTrue(result.compareTo(BigDecimal.ZERO) < 0);
    verify(exchangeRateCacheService, times(1)).getDollarRate();
  }

  @Test
  void convertBrlToUsd_WithRoundingMode_ShouldRoundCorrectly() {
    BigDecimal price = new BigDecimal("145000.00");
    BigDecimal rate = new BigDecimal("5.80");

    when(exchangeRateCacheService.getDollarRate()).thenReturn(rate);

    BigDecimal result = exchangeRateService.convertBrlToUsd(price);

    assertNotNull(result);
    assertEquals(new BigDecimal("25000.00"), result);
    assertEquals(2, result.scale());
    verify(exchangeRateCacheService, times(1)).getDollarRate();
  }

  @Test
  void convertBrlToUsd_WithPriceZero_ShouldReturnZero() {
    BigDecimal zeroPrice = BigDecimal.ZERO;
    when(exchangeRateCacheService.getDollarRate()).thenReturn(dollarRate);

    BigDecimal result = exchangeRateService.convertBrlToUsd(zeroPrice);

    assertNotNull(result);
    assertEquals(0, result.compareTo(BigDecimal.ZERO));
    verify(exchangeRateCacheService, times(1)).getDollarRate();
  }

  @Test
  void convertBrlToUsd_WithHighPrecisionRate_ShouldReturnCorrectValue() {
    BigDecimal highPrecisionRate = new BigDecimal("5.8754");
    BigDecimal price = new BigDecimal("145000.00");

    when(exchangeRateCacheService.getDollarRate()).thenReturn(highPrecisionRate);

    BigDecimal result = exchangeRateService.convertBrlToUsd(price);

    assertNotNull(result);
    assertEquals(new BigDecimal("24679.17"), result);
    assertEquals(2, result.scale());
    verify(exchangeRateCacheService, times(1)).getDollarRate();
  }
}