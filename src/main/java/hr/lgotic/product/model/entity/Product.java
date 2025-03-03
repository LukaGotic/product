package hr.lgotic.product.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {

  @Column(length = 10, nullable = false, unique = true)
  private String code;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private BigDecimal priceEur;

  @Column(nullable = false)
  private boolean isAvailable;
}
