package com.alekseenko.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

  @GetMapping(value = {"/", ""})
  public String index() {
    return "redirect:/course";
  }

  @GetMapping(value = {"/server_error"})
  public String triggerServerError() {
    return "redirect:/course";
  }

  @PostMapping(value = {"/general_error"})
  public String triggerGeneralError() {
    return "redirect:/course";
  }

}
