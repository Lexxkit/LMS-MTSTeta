package com.alekseenko.lms.validator.impl;

import com.alekseenko.lms.validator.TitleValidator;
import com.alekseenko.lms.validator.chain.PasswordCheck;
import com.alekseenko.lms.validator.chain.TitleChecker;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordValidator implements TitleValidator {

  Logger logger = LoggerFactory.getLogger(PasswordValidator.class);

  private TitleChecker passwordChecker = new PasswordCheck();

  @Override
  public boolean isValid(List<String> wordTitle) {
    logger.info("Password validator entered");
    return passwordChecker.check(wordTitle);
  }
}
