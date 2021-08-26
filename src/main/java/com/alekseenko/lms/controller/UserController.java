package com.alekseenko.lms.controller;

import com.alekseenko.lms.dto.RoleDto;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import java.security.Principal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

  private final RoleService roleService;
  private final UserService userService;

  @ModelAttribute("roles")
  public List<RoleDto> rolesAttribute() {
    return roleService.findAllRoles();
  }

  @GetMapping("")
  public String userForm(Principal principal) {
    if (principal != null) {
      return "redirect:/profile";
    }
    return "redirect:/login";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/{id}")
  public String currentUserForm(Model model, @PathVariable("id") Long id) {
    model.addAttribute("user", userService.getUserById(id));
    model.addAttribute("activePage", "users");
    return "user-create";
  }

}
