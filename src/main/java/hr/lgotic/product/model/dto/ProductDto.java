package hr.lgotic.product.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonPropertyOrder({
  "id",
  "code",
  "name",
  "priceEur",
  "priceUsd",
  "available",
  "exchangeRateAvailable"
})
public class ProductDto {
  private Long id;

  @Size(min = 10, max = 10) private String code;

  @NotNull private String name;

  @NotNull @Min(value = 0) private BigDecimal priceEur;

  @Min(value = 0) private BigDecimal priceUsd;

  private boolean available;

  private boolean exchangeRateAvailable;
}
