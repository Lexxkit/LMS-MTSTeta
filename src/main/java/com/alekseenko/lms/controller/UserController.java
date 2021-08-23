package com.alekseenko.lms.controller;

import com.alekseenko.lms.dto.RoleDto;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  private final RoleService roleService;
  private final UserService userService;

  @Autowired
  public UserController(RoleService roleService, UserService userService) {
    this.roleService = roleService;
    this.userService = userService;
  }

  @ModelAttribute("roles")
  public List<RoleDto> rolesAttribute() {
    return roleService.findAllRoles();
  }

  @GetMapping
  public String userForm(Model model, Principal principal) {
    if (principal != null) {
      return "redirect:/profile";
    }
    return "redirect:/login";
  }

  @PostMapping()
  public String registerUser(@Valid @ModelAttribute("user") UserDto user,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "user-create";
    }

    userService.saveUser(user);
    return "redirect:/login";
  }

  @GetMapping("/registration")
  public String userRegistration(Model model) {
    model.addAttribute("user", userService.getRegistrationTemplate());
    return "user-create";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/{id}")
  public String currentUserForm(Model model, @PathVariable("id") Long id) {
    model.addAttribute("user", userService.getUserById(id));
    model.addAttribute("activePage", "users");
    return "user-create";
  }
}
