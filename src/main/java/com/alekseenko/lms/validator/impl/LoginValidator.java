package com.alekseenko.lms.validator.impl;

import com.alekseenko.lms.validator.TitleValidator;
import com.alekseenko.lms.validator.chain.LoginCheck;
import com.alekseenko.lms.validator.chain.TitleChecker;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginValidator implements TitleValidator {

  Logger logger = LoggerFactory.getLogger(LoginValidator.class);

  private TitleChecker loginChecker = new LoginCheck();

  @Override
  public boolean isValid(List<String> wordTitle) {
    logger.info("Login validator entered");
    return loginChecker.check(wordTitle);
  }
}
