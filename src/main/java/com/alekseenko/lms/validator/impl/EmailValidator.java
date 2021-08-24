package com.alekseenko.lms.validator.impl;

import com.alekseenko.lms.validator.TitleValidator;
import com.alekseenko.lms.validator.chain.EmailCheck;
import com.alekseenko.lms.validator.chain.TitleChecker;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailValidator implements TitleValidator {

  Logger logger = LoggerFactory.getLogger(EmailValidator.class);

  private TitleChecker emailChecker = new EmailCheck();

  @Override
  public boolean isValid(List<String> wordTitle) {
    logger.info("Email validator entered");
    return emailChecker.check(wordTitle);
  }
}
