package bitlab.to2024g1security.service;

import bitlab.to2024g1security.entity.Role;
import bitlab.to2024g1security.entity.User;
import bitlab.to2024g1security.repository.RoleRepository;
import bitlab.to2024g1security.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof AnonymousAuthenticationToken) {
      return null;
    } else {
      return (User) authentication.getPrincipal();
    }
  }

  public String create(User user, String rePassword) {
    Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
    if (userOptional.isPresent()) {
      return "Username is busy!";
    }

    if (!user.getPassword().equals(rePassword)) {
      return "Passwords are not same!";
    }

    final String encodedPassword = passwordEncoder.encode(rePassword);
    user.setPassword(encodedPassword);

    Role userRole = roleRepository.findRoleUser();
    user.setRoles(Collections.singletonList(userRole));

    userRepository.save(user);
    return "Account created successfully!";
  }

  public String changePassword(String currentPassword, String newPassword, String reNewPassword) { // qwe, qwerty, qwerty
    boolean correctPassword = passwordEncoder.matches(currentPassword, getCurrentUser().getPassword());
    if (!correctPassword) { // проверка текущего пароля
      return "Incorrect current password!";
    }

    if (!newPassword.equals(reNewPassword)) { // сравнение новых паролей
      return "Passwords are not same!";
    }

    getCurrentUser().setPassword(passwordEncoder.encode(newPassword));

    userRepository.save(getCurrentUser());

    return "Password successfully changed!";
  }
}
