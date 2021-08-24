package com.alekseenko.lms.validator.chain;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpecialCharactersCheck extends TitleChecker {

  Logger logger = LoggerFactory.getLogger(SpecialCharactersCheck.class);

  /**
   * Символы \r, \t, \n, являются запрещенными
   */
  public boolean check(List<String> wordTitle) {
    logger.info("search for special characters...");
    if (wordTitle.stream().anyMatch(SpecialCharactersCheck::isSpecialCharacter)) {
      logger.info("SpecialCharactersCheck - SPECIAL CHARACTERS FOUND");
      return false;
    }
    return checkNext(wordTitle);
  }

  private static boolean isSpecialCharacter(String s) {
    return (s.equals("\\r") || s.equals("\\t") || s.equals("\\n"));
  }
}
