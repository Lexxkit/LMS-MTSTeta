package com.alekseenko.lms.validator.impl;

import com.alekseenko.lms.validator.TitleValidator;
import com.alekseenko.lms.validator.chain.PhoneNumberCheck;
import com.alekseenko.lms.validator.chain.TitleChecker;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneNumberValidator implements TitleValidator {

  Logger logger = LoggerFactory.getLogger(PhoneNumberValidator.class);

  private TitleChecker phoneNumberCheck = new PhoneNumberCheck();

  @Override
  public boolean isValid(List<String> wordTitle) {
    logger.info("Password validator entered");
    return phoneNumberCheck.check(wordTitle);
  }
}
