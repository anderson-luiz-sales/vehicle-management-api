package com.vehiclemanagement.service;

import com.vehiclemanagement.client.AwesomeApiClient;
import com.vehiclemanagement.client.FrankfurterApiClient;
import com.vehiclemanagement.dto.client.response.AwesomeApiResponseDTO;
import com.vehiclemanagement.dto.client.response.FrankfurterResponseDTO;
import com.vehiclemanagement.dto.client.response.RatesDTO;
import com.vehiclemanagement.dto.client.response.UsdBrlDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateCacheServiceTest {

  @InjectMocks
  private ExchangeRateCacheService exchangeRateCacheService;

  @Mock
  private AwesomeApiClient awesomeApiClient;

  @Mock
  private FrankfurterApiClient frankfurterApiClient;

  @Test
  void getDollarRate_WhenAwesomeApiAvailable_ShouldReturnRateFromAwesomeApi() {
    UsdBrlDTO usdBrlDTO = new UsdBrlDTO();
    usdBrlDTO.setBid("5.80");

    AwesomeApiResponseDTO awesomeResponse = new AwesomeApiResponseDTO();
    awesomeResponse.setUsdbrl(usdBrlDTO);

    when(awesomeApiClient.getDollarRate()).thenReturn(awesomeResponse);

    BigDecimal result = exchangeRateCacheService.getDollarRate();

    assertNotNull(result);
    assertEquals(new BigDecimal("5.80"), result);
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, never()).getDollarRate();
  }

  @Test
  void getDollarRate_WhenAwesomeApiFails_ShouldReturnRateFromFrankfurter() {
    RatesDTO ratesDTO = new RatesDTO();
    ratesDTO.setBrl(new BigDecimal("5.80"));

    FrankfurterResponseDTO frankfurterResponse = new FrankfurterResponseDTO();
    frankfurterResponse.setRates(ratesDTO);

    when(awesomeApiClient.getDollarRate()).thenThrow(new RuntimeException("Awesome API error"));
    when(frankfurterApiClient.getDollarRate()).thenReturn(frankfurterResponse);

    BigDecimal result = exchangeRateCacheService.getDollarRate();

    assertNotNull(result);
    assertEquals(new BigDecimal("5.80"), result);
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, times(1)).getDollarRate();
  }

  @Test
  void getDollarRate_WhenAwesomeApiThrowsNullPointerException_ShouldReturnRateFromFrankfurter() {
    RatesDTO ratesDTO = new RatesDTO();
    ratesDTO.setBrl(new BigDecimal("5.80"));

    FrankfurterResponseDTO frankfurterResponse = new FrankfurterResponseDTO();
    frankfurterResponse.setRates(ratesDTO);

    when(awesomeApiClient.getDollarRate()).thenThrow(new NullPointerException("Response is null"));
    when(frankfurterApiClient.getDollarRate()).thenReturn(frankfurterResponse);

    BigDecimal result = exchangeRateCacheService.getDollarRate();

    assertNotNull(result);
    assertEquals(new BigDecimal("5.80"), result);
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, times(1)).getDollarRate();
  }

  @Test
  void getDollarRate_WhenAwesomeApiReturnsDifferentRate_ShouldReturnCorrectRate() {
    UsdBrlDTO usdBrlDTO = new UsdBrlDTO();
    usdBrlDTO.setBid("6.20");

    AwesomeApiResponseDTO awesomeResponse = new AwesomeApiResponseDTO();
    awesomeResponse.setUsdbrl(usdBrlDTO);

    when(awesomeApiClient.getDollarRate()).thenReturn(awesomeResponse);

    BigDecimal result = exchangeRateCacheService.getDollarRate();

    assertNotNull(result);
    assertEquals(new BigDecimal("6.20"), result);
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, never()).getDollarRate();
  }

  @Test
  void getDollarRate_WhenFrankfurterApiReturnsDifferentRate_ShouldReturnCorrectRate() {
    RatesDTO ratesDTO = new RatesDTO();
    ratesDTO.setBrl(new BigDecimal("6.20"));

    FrankfurterResponseDTO frankfurterResponse = new FrankfurterResponseDTO();
    frankfurterResponse.setRates(ratesDTO);

    when(awesomeApiClient.getDollarRate()).thenThrow(new RuntimeException("Awesome API error"));
    when(frankfurterApiClient.getDollarRate()).thenReturn(frankfurterResponse);

    BigDecimal result = exchangeRateCacheService.getDollarRate();

    assertNotNull(result);
    assertEquals(new BigDecimal("6.20"), result);
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, times(1)).getDollarRate();
  }

  @Test
  void getDollarRate_WhenBothApisFail_ShouldThrowException() {
    when(awesomeApiClient.getDollarRate()).thenThrow(new RuntimeException("Awesome API error"));
    when(frankfurterApiClient.getDollarRate()).thenThrow(new RuntimeException("Frankfurter API error"));

    assertThrows(Exception.class, () -> exchangeRateCacheService.getDollarRate());
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, times(1)).getDollarRate();
  }

  @Test
  void getDollarRate_WhenAwesomeApiReturnsZeroRate_ShouldReturnZero() {
    UsdBrlDTO usdBrlDTO = new UsdBrlDTO();
    usdBrlDTO.setBid("0.00");

    AwesomeApiResponseDTO awesomeResponse = new AwesomeApiResponseDTO();
    awesomeResponse.setUsdbrl(usdBrlDTO);

    when(awesomeApiClient.getDollarRate()).thenReturn(awesomeResponse);

    BigDecimal result = exchangeRateCacheService.getDollarRate();

    assertNotNull(result);
    assertEquals(BigDecimal.ZERO.setScale(2), result);
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, never()).getDollarRate();
  }

  @Test
  void getDollarRate_WhenFrankfurterApiReturnsZeroRate_ShouldReturnZero() {
    RatesDTO ratesDTO = new RatesDTO();
    ratesDTO.setBrl(BigDecimal.ZERO);

    FrankfurterResponseDTO frankfurterResponse = new FrankfurterResponseDTO();
    frankfurterResponse.setRates(ratesDTO);

    when(awesomeApiClient.getDollarRate()).thenThrow(new RuntimeException("Awesome API error"));
    when(frankfurterApiClient.getDollarRate()).thenReturn(frankfurterResponse);

    BigDecimal result = exchangeRateCacheService.getDollarRate();

    assertNotNull(result);
    assertEquals(BigDecimal.ZERO, result);
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, times(1)).getDollarRate();
  }

  @Test
  void getDollarRate_ShouldCacheResult() {
    UsdBrlDTO usdBrlDTO = new UsdBrlDTO();
    usdBrlDTO.setBid("5.80");

    AwesomeApiResponseDTO awesomeResponse = new AwesomeApiResponseDTO();
    awesomeResponse.setUsdbrl(usdBrlDTO);

    when(awesomeApiClient.getDollarRate()).thenReturn(awesomeResponse);

    BigDecimal result1 = exchangeRateCacheService.getDollarRate();
    BigDecimal result2 = exchangeRateCacheService.getDollarRate();

    assertNotNull(result1);
    assertNotNull(result2);
    assertEquals(result1, result2);
    verify(awesomeApiClient, times(2)).getDollarRate();
    verify(frankfurterApiClient, never()).getDollarRate();
  }

  @Test
  void getDollarRate_WhenAwesomeApiReturnsNullUsdbrl_ShouldUseFrankfurter() {
    AwesomeApiResponseDTO awesomeResponse = new AwesomeApiResponseDTO();
    awesomeResponse.setUsdbrl(null);

    RatesDTO ratesDTO = new RatesDTO();
    ratesDTO.setBrl(new BigDecimal("5.80"));

    FrankfurterResponseDTO frankfurterResponse = new FrankfurterResponseDTO();
    frankfurterResponse.setRates(ratesDTO);

    when(awesomeApiClient.getDollarRate()).thenReturn(awesomeResponse);
    when(frankfurterApiClient.getDollarRate()).thenReturn(frankfurterResponse);

    BigDecimal result = exchangeRateCacheService.getDollarRate();

    assertNotNull(result);
    assertEquals(new BigDecimal("5.80"), result);
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, times(1)).getDollarRate();
  }

  @Test
  void getDollarRate_WhenFrankfurterApiReturnsNullRates_ShouldThrowException() {
    FrankfurterResponseDTO frankfurterResponse = new FrankfurterResponseDTO();
    frankfurterResponse.setRates(null);

    when(awesomeApiClient.getDollarRate()).thenThrow(new RuntimeException("Awesome API error"));
    when(frankfurterApiClient.getDollarRate()).thenReturn(frankfurterResponse);

    assertThrows(NullPointerException.class, () -> exchangeRateCacheService.getDollarRate());
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, times(1)).getDollarRate();
  }

  @Test
  void getDollarRate_WhenAwesomeApiBidIsEmpty_ShouldUseFrankfurter() {
    UsdBrlDTO usdBrlDTO = new UsdBrlDTO();
    usdBrlDTO.setBid("");

    AwesomeApiResponseDTO awesomeResponse = new AwesomeApiResponseDTO();
    awesomeResponse.setUsdbrl(usdBrlDTO);

    RatesDTO ratesDTO = new RatesDTO();
    ratesDTO.setBrl(new BigDecimal("5.80"));

    FrankfurterResponseDTO frankfurterResponse = new FrankfurterResponseDTO();
    frankfurterResponse.setRates(ratesDTO);

    when(awesomeApiClient.getDollarRate()).thenReturn(awesomeResponse);
    when(frankfurterApiClient.getDollarRate()).thenReturn(frankfurterResponse);

    BigDecimal result = exchangeRateCacheService.getDollarRate();

    assertNotNull(result);
    assertEquals(new BigDecimal("5.80"), result);
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, times(1)).getDollarRate();
  }

  @Test
  void getDollarRate_WhenAwesomeApiBidIsInvalid_ShouldUseFrankfurter() {
    UsdBrlDTO usdBrlDTO = new UsdBrlDTO();
    usdBrlDTO.setBid("invalid");

    AwesomeApiResponseDTO awesomeResponse = new AwesomeApiResponseDTO();
    awesomeResponse.setUsdbrl(usdBrlDTO);

    RatesDTO ratesDTO = new RatesDTO();
    ratesDTO.setBrl(new BigDecimal("5.80"));

    FrankfurterResponseDTO frankfurterResponse = new FrankfurterResponseDTO();
    frankfurterResponse.setRates(ratesDTO);

    when(awesomeApiClient.getDollarRate()).thenReturn(awesomeResponse);
    when(frankfurterApiClient.getDollarRate()).thenReturn(frankfurterResponse);

    BigDecimal result = exchangeRateCacheService.getDollarRate();

    assertNotNull(result);
    assertEquals(new BigDecimal("5.80"), result);
    verify(awesomeApiClient, times(1)).getDollarRate();
    verify(frankfurterApiClient, times(1)).getDollarRate();
  }
}