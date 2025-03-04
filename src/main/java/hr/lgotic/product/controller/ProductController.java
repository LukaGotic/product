package hr.lgotic.product.controller;

import hr.lgotic.product.model.dto.ProductDto;
import hr.lgotic.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Product API", description = "Operations related to products")
@SecurityRequirement(name = "Authorization")
public class ProductController {
  private final ProductService productService;

  @GetMapping
  @Operation(summary = "Get all products", description = "Returns a list of all products")
  public List<ProductDto> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get a specific product by ID",
      description = "Fetches a single product using its unique ID")
  public ProductDto getProductById(@PathVariable Long id) {
    return productService.getProductById(id);
  }

  @PostMapping
  @Operation(summary = "Create a new product", description = "Adds a new product to the database")
  public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
    ProductDto savedProduct = productService.createProduct(productDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
  }
}
