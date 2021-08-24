package com.alekseenko.lms.validator.chain;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginCheck extends TitleChecker {

  Logger logger = LoggerFactory.getLogger(LoginCheck.class);

  private static boolean isSpecialCharacter(String s) {
    String lowerCaseWord = s.toLowerCase();
    return !(lowerCaseWord.matches(
        "([A-Za-z0-9-@#$%^&+=]+)"));
  }

  /**
   * Логин. Можно ввести только латиницу и спецсимволы. В случае несоответствия вводимых символов
   * пользователю должна быть выведена ошибка “Поле Логин должно содержать только латиницу и/или
   * спец.символы”.
   */
  public boolean check(List<String> wordTitle) {
    logger.info("validation email...");
    if (wordTitle.stream().anyMatch(LoginCheck::isSpecialCharacter)) {
      logger.info("LoginCheck - INCORRECT LOGIN FORMAT");
      return false;
    }
    return checkNext(wordTitle);
  }
}
