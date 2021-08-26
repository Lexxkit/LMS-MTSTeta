package com.alekseenko.lms.controller;

import com.alekseenko.lms.domain.VerificationToken;
import com.alekseenko.lms.dto.RoleDto;
import com.alekseenko.lms.dto.UserDto;
import com.alekseenko.lms.event.OnRegistrationCompleteEvent;
import com.alekseenko.lms.exception.UserAlreadyRegisteredException;
import com.alekseenko.lms.service.RoleService;
import com.alekseenko.lms.service.UserService;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserRegistrationController {

  private final UserService userService;
  private final ApplicationEventPublisher eventPublisher;
  private final MessageSource messages;
  private final Locale locale;
  private final RoleService roleService;

  @ModelAttribute("roles")
  public List<RoleDto> rolesAttribute() {
    return roleService.findAllRoles();
  }

  @PostMapping("")
  public String registerUser(@Valid @ModelAttribute("user") UserDto user,
      BindingResult bindingResult,
      HttpServletRequest request,
      Authentication authentication) {
    if (bindingResult.hasErrors()) {
      log.info("Invalid user submitted");
      return "user-create";
    }

    try {
      var newUserAccount = userService.registerNewUserAccount(user);
      String appUrl = request.getContextPath();
      eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newUserAccount,
          locale, appUrl));
    } catch (UserAlreadyRegisteredException e) {
      bindingResult.rejectValue(e.getField(), "error.user", e.getMessage());
      return "user-create";
    } catch (RuntimeException ex) {
      log.error(ex.getMessage());
      bindingResult.rejectValue("email", "error.user",
          messages.getMessage("message.fail.emailFailed", null, locale));
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

  @GetMapping("/registrationConfirm")
  public String confirmRegistration(Model model,
      @RequestParam("token") String token) {

    VerificationToken verificationToken = userService.getVerificationToken(token);
    if (verificationToken == null) {
      var message = messages.getMessage("auth.message.invalidToken", null, locale);
      model.addAttribute("message", message);
      model.addAttribute("class", "text-danger");
      return "registration_result";
    }

    var user = verificationToken.getUser();
    var cal = Calendar.getInstance();

    if (user.isEnabled()) {
      model.addAttribute("message",
          messages.getMessage("message.regAlreadyConfirmed", null, locale));
      model.addAttribute("class", "text-success");
      return "registration_result";
    } else if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      model.addAttribute("message",
          messages.getMessage("auth.message.expired", null, locale));
      model.addAttribute("class", "text-danger");
    } else {
      model.addAttribute("message",
          messages.getMessage("message.regSuccConfirmed", null, locale));
      model.addAttribute("class", "text-success");
      user.setEnabled(true);
      userService.saveUser(user);
    }
    return "registration_result";
  }

}
