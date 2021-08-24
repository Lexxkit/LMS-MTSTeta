package com.alekseenko.lms.validator.chain;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class phoneNumberCheck extends TitleChecker {

  Logger logger = LoggerFactory.getLogger(phoneNumberCheck.class);

  private static boolean isSpecialCharacter(String s) {
    return (s.equals("\\r") || s.equals("\\t") || s.equals("\\n"));
  }

  /**
   * На поле пароль должна быть валидация: латиница, спецсимволы, не менее 8 символов.
   */
  public boolean check(List<String> wordTitle) {
    logger.info("search for special characters...");
    if (wordTitle.stream().anyMatch(phoneNumberCheck::isSpecialCharacter)) {
      logger.info("SpecialCharactersCheck - SPECIAL CHARACTERS FOUND");
      return false;
    }
    return checkNext(wordTitle);
  }
}
