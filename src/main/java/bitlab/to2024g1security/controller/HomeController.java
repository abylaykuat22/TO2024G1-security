package bitlab.to2024g1security.controller;

import bitlab.to2024g1security.entity.User;
import bitlab.to2024g1security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final UserService userService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/")
  public String homePage() {
    return "welcome";
  }

  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }

  @GetMapping("/forbidden")
  public String forbiddenPage() {
    return "forbidden";
  }

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
  @GetMapping("/profile")
  public String profilePage() {
//    User user = userService.getCurrentUser();
    return "profile";
  }
}
