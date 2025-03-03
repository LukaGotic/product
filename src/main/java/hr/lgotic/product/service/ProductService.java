package hr.lgotic.product.service;

import hr.lgotic.product.exception.ProductNotFoundException;
import hr.lgotic.product.model.dto.ProductDto;
import hr.lgotic.product.model.entity.Product;
import hr.lgotic.product.model.mapper.ProductMapper;
import hr.lgotic.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public List<ProductDto> getAllProducts() {
    return productRepository.findAll().stream()
        .map(productMapper::toDto)
        .collect(Collectors.toList());
  }

  public ProductDto getProductById(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    return productMapper.toDto(product);
  }

  @Transactional
  public ProductDto createProduct(ProductDto productDto) {
    Product product = productMapper.toEntity(productDto);
    Product savedProduct = productRepository.save(product);
    return productMapper.toDto(savedProduct);
  }
}
