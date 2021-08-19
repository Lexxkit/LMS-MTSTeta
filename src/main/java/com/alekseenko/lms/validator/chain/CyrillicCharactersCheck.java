package com.alekseenko.lms.validator.chain;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CyrillicCharactersCheck extends TitleChecker {

  Logger logger = LoggerFactory.getLogger(CyrillicCharactersCheck.class);

  /**
   * Ищем русские буквы
   */
  public boolean check(List<String> wordTitle) {
    logger.info("latin search...");
    for (String s : wordTitle) {
      if (s.toLowerCase().matches("^[a-zA-Z]*$")) {
        logger.info("CyrillicCharactersCheck - FOUND LATIN");
        return false;
      }
    }
    return checkNext(wordTitle);
  }
}
