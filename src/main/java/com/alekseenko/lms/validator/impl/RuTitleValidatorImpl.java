package com.alekseenko.lms.validator.impl;

import com.alekseenko.lms.validator.TitleValidator;
import com.alekseenko.lms.validator.chain.CyrillicCharactersCheck;
import com.alekseenko.lms.validator.chain.RuCapitalLetterCheck;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuTitleValidatorImpl extends AnyTitleValidator implements TitleValidator {

  Logger logger = LoggerFactory.getLogger(RuTitleValidatorImpl.class);

  @Override
  public boolean isValid(List<String> wordTitle) {
    logger.debug("RuTitleValidatorImpl entered");
    commonChain().linkWith(new RuCapitalLetterCheck()).linkWith(new CyrillicCharactersCheck());
    return super.titleChecker.check(wordTitle);
  }
}
