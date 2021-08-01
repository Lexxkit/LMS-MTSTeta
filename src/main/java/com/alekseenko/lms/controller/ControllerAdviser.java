package com.alekseenko.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerAdviser {

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
}
