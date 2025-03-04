package hr.lgotic.product.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import hr.lgotic.product.service.ExchangeRateService;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class ExchangeRateServiceTest {
  private static final String HNB_API_URL = "https://api.hnb.hr/tecajn-eur/v3?valuta=USD";

  @InjectMocks private ExchangeRateService exchangeRateService;

  @Mock private RestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenApiReturnsValidResponse_thenExchangeRateIsReturned() {
    Map<String, String>[] response =
        new Map[] {
          Map.of(
              "srednji_tecaj", "1,046500",
              "valuta", "USD")
        };
    when(restTemplate.getForObject(HNB_API_URL, Map[].class)).thenReturn(response);

    // Execute method
    Optional<BigDecimal> exchangeRate = exchangeRateService.getExchangeRate();

    // Verify results
    assertTrue(exchangeRate.isPresent());
    assertEquals(new BigDecimal("1.046500"), exchangeRate.get()); // Converted correctly
  }

  @Test
  void whenApiReturnsEmptyResponse_thenReturnEmptyOptional() {
    when(restTemplate.getForObject(HNB_API_URL, Map[].class)).thenReturn(new Map[] {});

    // Execute method
    Optional<BigDecimal> exchangeRate = exchangeRateService.getExchangeRate();

    // Verify results
    assertFalse(exchangeRate.isPresent());
  }

  @Test
  void whenApiResponseIsInvalid_thenReturnEmptyOptional() {
    Map<String, String>[] response = new Map[] {Map.of("valuta", "USD")};
    when(restTemplate.getForObject(HNB_API_URL, Map[].class)).thenReturn(response);

    // Execute method
    Optional<BigDecimal> exchangeRate = exchangeRateService.getExchangeRate();

    // Verify results
    assertFalse(exchangeRate.isPresent());
  }

  @Test
  void whenApiThrowsException_thenReturnEmptyOptional() {
    when(restTemplate.getForObject(HNB_API_URL, Map[].class))
        .thenThrow(new RuntimeException("API timeout"));

    // Execute method
    Optional<BigDecimal> exchangeRate = exchangeRateService.getExchangeRate();

    // Verify results
    assertFalse(exchangeRate.isPresent());
  }
}
