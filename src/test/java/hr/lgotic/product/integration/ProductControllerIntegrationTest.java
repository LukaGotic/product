package hr.lgotic.product.integration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.lgotic.product.model.dto.ProductDto;
import hr.lgotic.product.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private ProductRepository productRepository;

  private ProductDto testProduct;
  private String token;

  @BeforeEach
  void setUp() throws Exception {
    productRepository.deleteAll();
    this.token = obtainAccessToken();

    testProduct =
        ProductDto.builder()
            .code("PROD123456")
            .name("Test Product")
            .priceEur(new BigDecimal("99.99"))
            .available(true)
            .build();
  }

  private String obtainAccessToken() throws Exception {
    String loginJson =
        objectMapper.writeValueAsString(
            Map.of(
                "username", "user",
                "password", "test"));

    String response =
        mockMvc
            .perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(loginJson))
            .andReturn()
            .getResponse()
            .getContentAsString();

    return response.trim();
  }

  @Test
  void whenCreateProduct_thenReturnsCreatedProduct() throws Exception {
    String productJson = objectMapper.writeValueAsString(testProduct);

    String response =
        mockMvc
            .perform(
                post("/api/products")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(productJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.code", is(testProduct.getCode())))
            .andExpect(jsonPath("$.name", is(testProduct.getName())))
            .andExpect(jsonPath("$.priceEur", is(99.99)))
            .andExpect(jsonPath("$.available", is(true)))
            .andReturn()
            .getResponse()
            .getContentAsString();

    ProductDto createdProduct = objectMapper.readValue(response, ProductDto.class);
    Long productId = createdProduct.getId();

    mockMvc
        .perform(get("/api/products/{id}", productId).header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(productId.intValue())))
        .andExpect(jsonPath("$.code", is(testProduct.getCode())))
        .andExpect(jsonPath("$.name", is(testProduct.getName())))
        .andExpect(jsonPath("$.priceEur", is(99.99)))
        .andExpect(jsonPath("$.available", is(true)));
  }

  @Test
  void whenGetProductById_AndExists_thenReturnsProduct() throws Exception {
    String productJson = objectMapper.writeValueAsString(testProduct);
    String response =
        mockMvc
            .perform(
                post("/api/products")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(productJson))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

    ProductDto createdProduct = objectMapper.readValue(response, ProductDto.class);
    Long productId = createdProduct.getId();

    mockMvc
        .perform(get("/api/products/{id}", productId).header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(productId.intValue())))
        .andExpect(jsonPath("$.code", is(testProduct.getCode())))
        .andExpect(jsonPath("$.name", is(testProduct.getName())))
        .andExpect(jsonPath("$.priceEur", is(99.99)))
        .andExpect(jsonPath("$.available", is(true)));
  }

  @Test
  void whenGetProductById_AndDoesNotExist_thenReturnsNotFound() throws Exception {
    mockMvc
        .perform(get("/api/products/{id}", 9999).header("Authorization", "Bearer " + token))
        .andExpect(status().isNotFound());
  }
}
