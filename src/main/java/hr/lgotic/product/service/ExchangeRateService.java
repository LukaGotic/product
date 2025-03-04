package hr.lgotic.product.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
  private static final String HNB_API_URL = "https://api.hnb.hr/tecajn-eur/v3?valuta=USD";
  private final RestTemplate restTemplate;

  @Cacheable(value = "exchangeRate")
  public Optional<BigDecimal> getExchangeRate() {
    try {
      Map[] response = restTemplate.getForObject(HNB_API_URL, Map[].class);
      if (response != null && response.length > 0) {
        String rateString = response[0].get("srednji_tecaj").toString().replace(",", ".");
        return Optional.of(new BigDecimal(rateString));
      }
    } catch (Exception e) {
      return Optional.empty();
    }
    return Optional.empty();
  }
}
