package com.vehiclemanagement.controller;

import com.vehiclemanagement.service.ExchangeRateService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/exchange")
@RequiredArgsConstructor
public class ExchangeController {

  private final ExchangeRateService exchangeRateService;

  @GetMapping("/usd")
  public BigDecimal getDollarRate() {
    return exchangeRateService.getDollarRate();
  }

}
