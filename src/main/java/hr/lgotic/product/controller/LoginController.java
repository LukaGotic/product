package hr.lgotic.product.controller;

import hr.lgotic.product.model.dto.AuthRequest;
import hr.lgotic.product.security.JwtUtil;
import hr.lgotic.product.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final CustomUserDetailsService customUserDetailsService;

  @PostMapping("/login")
  public String login(@RequestBody AuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
    return jwtUtil.generateToken(userDetails.getUsername());
  }
}
