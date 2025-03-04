package hr.lgotic.product.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import hr.lgotic.product.service.ExchangeRateService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ExchangeRateServiceIntegrationTest {

  @Autowired private ExchangeRateService exchangeRateService;

  @Test
  void whenCallingRealApi_thenExchangeRateIsReturned() {
    Optional<BigDecimal> exchangeRate = exchangeRateService.getExchangeRate();

    // Verify results
    assertTrue(exchangeRate.isPresent(), "Exchange rate should be present");
    assertTrue(
        exchangeRate.get().compareTo(BigDecimal.ZERO) > 0, "Exchange rate should be positive");
  }
}
