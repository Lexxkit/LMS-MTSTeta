package com.alekseenko.lms.validator.chain;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailCheck extends TitleChecker {

  Logger logger = LoggerFactory.getLogger(EmailCheck.class);

  private static boolean isSpecialCharacter(String s) {
    String lowerCaseWord = s.toLowerCase();
    return !(lowerCaseWord.matches(
        /*В данном регулярном выражении указанно, что строка может состоять с _A-Za-z0-9
         допускаемых символов, после чего идет символ @ и опять допускаемые символы*/
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"));
  }

  /**
   * На поле email также должна стоять валидация (проверка маски) + проверка занятости. В случае
   * несоответствия вводимых символов маске пользователю должна быть выведена ошибка “Некорректный
   * формат email
   */
  public boolean check(List<String> wordTitle) {
    logger.info("validation email...");
    if (wordTitle.stream().anyMatch(EmailCheck::isSpecialCharacter)) {
      logger.info("emailCheck - INCORRECT EMAIL FORMAT");
      return false;
    }
    return checkNext(wordTitle);
  }
}
