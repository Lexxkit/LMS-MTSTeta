package com.alekseenko.lms.validator.impl;

import com.alekseenko.lms.validator.TitleValidator;
import com.alekseenko.lms.validator.chain.EnCapitalLetterCheck;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnTitleValidator extends AnyTitleValidator implements TitleValidator {

  Logger logger = LoggerFactory.getLogger(EnTitleValidator.class);

  @Override
  public boolean isValid(List<String> wordTitle) {
    logger.info("EnTitleValidator entered");

    commonChain().linkWith(new EnCapitalLetterCheck());
    return super.titleChecker.check(wordTitle);
  }
}
