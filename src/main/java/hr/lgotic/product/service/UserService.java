package hr.lgotic.product.service;

import hr.lgotic.product.model.entity.User;
import hr.lgotic.product.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User registerUser(String username, String rawPassword) {
    if (userRepository.existsByUsername(username)) {
      throw new IllegalArgumentException("Username is already taken.");
    }

    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(rawPassword));

    return userRepository.save(user);
  }
}
