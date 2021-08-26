package com.alekseenko.lms.controller;

import com.alekseenko.lms.exception.AccessDeniedException;
import com.alekseenko.lms.exception.InternalServerException;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@AllArgsConstructor
public class ControllerAdviser {

  private final UserService service;

  @ModelAttribute("isDisabled")
  public Boolean checkIfUserEnabled(Authentication auth) {
    if (auth != null && auth.isAuthenticated()) {
      return service.checkIfUserEnabled(auth.getName());
    }
    return null;
  }

  @ExceptionHandler(NotFoundException.class)
  public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
    ModelAndView modelAndView = new ModelAndView("not_found");
    modelAndView.setStatus(HttpStatus.NOT_FOUND);
    return modelAndView;
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ModelAndView accessDeniedExceptionHandler(AccessDeniedException ex) {
    ModelAndView modelAndView = new ModelAndView("access_denied");
    modelAndView.setStatus(HttpStatus.FORBIDDEN);
    return modelAndView;
  }

  @ExceptionHandler(InternalServerException.class)
  public ModelAndView internalServerErrorHandler(InternalServerException ex) {
    ModelAndView modelAndView = new ModelAndView("internal_error");
    modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    return modelAndView;
  }

  @ExceptionHandler(IllegalStateException.class)
  public ModelAndView illegalStateExceptionHandler(IllegalStateException ex) {
    ModelAndView modelAndView = new ModelAndView("internal_error");
    modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    return modelAndView;
  }

}
