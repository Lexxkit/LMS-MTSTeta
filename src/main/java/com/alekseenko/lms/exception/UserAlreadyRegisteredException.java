package com.alekseenko.lms.exception;

import lombok.Getter;

@Getter
public class UserAlreadyRegisteredException extends Exception {

  private final String field;

  public UserAlreadyRegisteredException(String message, String field) {
    super(message);
    this.field = field;
  }
}
