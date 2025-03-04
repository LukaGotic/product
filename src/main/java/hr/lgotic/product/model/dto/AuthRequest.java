package hr.lgotic.product.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

  @NotBlank(message = "Username is required") @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters") private String username;

  @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") private String password;
}
