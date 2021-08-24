package com.alekseenko.lms.validator.impl;

import com.alekseenko.lms.validator.TitleValidator;
import com.alekseenko.lms.validator.chain.PhoneCheck;
import com.alekseenko.lms.validator.chain.TitleChecker;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneValidator implements TitleValidator {

  Logger logger = LoggerFactory.getLogger(PhoneValidator.class);

  private TitleChecker phoneChecker = new PhoneCheck();

  @Override
  public boolean isValid(List<String> wordTitle) {
    logger.info("Phone validator entered");
    return phoneChecker.check(wordTitle);
  }
}
