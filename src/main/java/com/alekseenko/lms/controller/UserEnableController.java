package com.alekseenko.lms.controller;

import com.alekseenko.lms.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserEnableController {

  private final UserService userService;

  @GetMapping("/{id}/isUserEnabled")
  @ResponseBody
  public Boolean isUserEnabled(@PathVariable("id") Long id) {
    return userService.checkIfUserEnabled(id);
  }

}
