package bitlab.to2024g1security.controller;

import bitlab.to2024g1security.entity.User;
import bitlab.to2024g1security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/user/create")
  public String createUser(User user, @RequestParam String rePassword) {
    String status = userService.create(user, rePassword);
    return "redirect:/login?status=" + status;
  }

  @PostMapping("/user/change-password")
  public String changePassword(@RequestParam String currentPassword,
      @RequestParam String newPassword, @RequestParam String reNewPassword) {
    String status = userService.changePassword(currentPassword, newPassword, reNewPassword);
    return "redirect:/profile?status=" + status;
  }
}
