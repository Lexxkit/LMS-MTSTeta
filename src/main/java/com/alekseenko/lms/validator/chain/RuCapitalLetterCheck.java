package com.alekseenko.lms.validator.chain;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Правила русских заголовков: Первое слово должно быть написано с большой буквы, остальные - с
 * маленькой.
 */
public class RuCapitalLetterCheck extends TitleChecker {

  Logger logger = LoggerFactory.getLogger(RuCapitalLetterCheck.class);

  public boolean check(List<String> wordTitle) {
    logger.info("search for uppercase cyrillic...");
    int FIRST_WORD = 1;
    final boolean isCapitalCharacter = wordTitle.stream()
        .skip(FIRST_WORD)
        .filter(s -> s.length() > 0)
        .anyMatch(s -> Character.isLowerCase(s.charAt(0)));

    if (Character.isLowerCase(wordTitle.get(0).charAt(0)) || !isCapitalCharacter) {
      logger.info("RuCapitalLetterCheck - SENTENCE DOES NOT START WITH A CAPITAL LETTER");
      return false;
    }
    return checkNext(wordTitle);
  }
}
