package com.alekseenko.lms.validator.chain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnCapitalLetterCheck extends TitleChecker {

  Logger logger = LoggerFactory.getLogger(EnCapitalLetterCheck.class);

  public boolean check(List<String> wordTitle) {
    logger.info("search for latin capital letters...");
    final Set<String> pretextList = Stream.of("a", "but", "for", "or", "not", "the", "an")
        .collect(Collectors.toSet());

    Set<String> filteredSet = wordTitle.stream()
        .filter(s -> s.length() > 0)
        .filter(s -> Character.isLowerCase(s.charAt(0)))
        .collect(Collectors.toSet());

    filteredSet.removeAll(pretextList);

    if (filteredSet.size() > 0) {
      logger.info("CapitalLetterCheck - FOUND NON CAPITAL LETTERS IN THE ENGLISH TITLE");
      return false;
    }
    return checkNext(wordTitle);
  }
}
