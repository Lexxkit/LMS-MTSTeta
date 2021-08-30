package com.alekseenko.lms.validator.chain;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneCheck extends TitleChecker {

  Logger logger = LoggerFactory.getLogger(PhoneCheck.class);

  private static boolean isPhoneNumber(String s) {
    String lowerCaseWord = s.toLowerCase();
    return !(lowerCaseWord.matches(
        "\\d{10}") || lowerCaseWord.matches(
        ""));
  }

  public boolean check(List<String> wordTitle) {
    logger.info("validation phonenumber...");
    if (wordTitle.stream().anyMatch(PhoneCheck::isPhoneNumber)) {
      logger.info("LoginCheck - INCORRECT PHONENUMBER FORMAT");
      return false;
    }
    return checkNext(wordTitle);
  }
}
