package com.alekseenko.lms.controller;

import com.alekseenko.lms.domain.VerificationToken;
import com.alekseenko.lms.dto.RoleDto;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.event.OnRegistrationCompleteEvent;
import com.alekseenko.lms.exception.UserAlreadyRegisteredException;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final RoleService roleService;
  private final UserService userService;
  private final ApplicationEventPublisher eventPublisher;
  private final MessageSource messages;

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

  @PostMapping("")
  public String registerUser(@Valid @ModelAttribute("user") UserDto user,
      HttpServletRequest request,
      BindingResult bindingResult, Authentication authentication) {
    if (bindingResult.hasErrors()) {
      return "user-create";
    }

    try {
      var newUserAccount = userService.registerNewUserAccount(user);
      String appUrl = request.getContextPath();
      eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newUserAccount,
          request.getLocale(), appUrl));
    } catch (UserAlreadyRegisteredException e) {
      bindingResult.rejectValue(e.getField(), "error.user", e.getMessage());
      return "user-create";
    } catch (RuntimeException ex) {
      bindingResult.rejectValue("email", "error.user",
          "Не получилось выслать письмо подтверждения на указанный email," +
              " свяжитесь с администратором для активации Вашего аккаунта!");
      return "user-create";
    }
    if (authentication != null && authentication.getAuthorities().stream().anyMatch(r ->
        (r.getAuthority().equals("ROLE_ADMIN") || (r.getAuthority().equals("ROLE_OWNER"))))) {
      return "redirect:/admin/user";
    }
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

  @GetMapping("/registrationConfirm")
  public String confirmRegistration(Model model,
      WebRequest request, @RequestParam("token") String token) {

    Locale locale = request.getLocale();

    VerificationToken verificationToken = userService.getVerificationToken(token);
    if (verificationToken == null) {
      String message = messages.getMessage("auth.message.invalidToken", null, locale);
      model.addAttribute("message", message);
      return "redirect:/badUser.html?lang=" + locale.getLanguage();
    }

    var user = verificationToken.getUser();
    Calendar cal = Calendar.getInstance();
    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      String messageValue = messages.getMessage("auth.message.expired", null, locale);
      model.addAttribute("message", messageValue);
      return "redirect:/badUser.html?lang=" + locale.getLanguage();
    }

    user.setEnabled(true);
    userService.saveUser(user);
    return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
  }

}
