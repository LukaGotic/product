package hr.lgotic.product.model.mapper;

import hr.lgotic.product.model.dto.ProductDto;
import hr.lgotic.product.model.entity.Product;
import hr.lgotic.product.service.ExchangeRateService;
import java.math.BigDecimal;
import java.util.Optional;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {

  @Autowired protected ExchangeRateService exchangeRateService;

  @Mapping(target = "priceUsd", ignore = true)
  @Mapping(target = "exchangeRateAvailable", ignore = true)
  public abstract ProductDto toDto(Product product);

  @Mapping(target = "id", ignore = true)
  public abstract Product toEntity(ProductDto productDto);

  @AfterMapping
  protected void setExchangeRate(
      @MappingTarget ProductDto.ProductDtoBuilder productDtoBuilder, Product product) {
    final Optional<BigDecimal> exchangeRateOpt = exchangeRateService.getExchangeRate();
    final boolean exchangeRateAvailable = exchangeRateOpt.isPresent();
    productDtoBuilder.exchangeRateAvailable(exchangeRateAvailable);

    exchangeRateOpt.ifPresent(
        rate -> productDtoBuilder.priceUsd(product.getPriceEur().multiply(rate)));
  }
}
