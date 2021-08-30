package com.alekseenko.lms.validator.chain;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpaceCheck extends TitleChecker {

  Logger logger = LoggerFactory.getLogger(SpaceCheck.class);

  /**
   * Слова в заголовках разделяются одним пробелом Строка не должна начинаться с пробела
   */
  public boolean check(List<String> wordTitle) {
    logger.debug("search for spaces...");
    if (wordTitle.get(0).startsWith(" ") || wordTitle.stream().anyMatch(SpaceCheck::isExtraSpace)) {
      logger.info("SpaceCheck - EXTRA SPACES FOUND");
      return false;
    }
    return checkNext(wordTitle);
  }

  private static boolean isExtraSpace(String s) {
    return s.equals("");
  }
}
