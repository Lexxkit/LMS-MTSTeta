package com.alekseenko.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/access_denied")
public class AccessDeniedController {

  @GetMapping
  public ModelAndView accessDenied() {
    ModelAndView modelAndView = new ModelAndView("access_denied");
    modelAndView.setStatus(HttpStatus.FORBIDDEN);
    return modelAndView;
  }
}
