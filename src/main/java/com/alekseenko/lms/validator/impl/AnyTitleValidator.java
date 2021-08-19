package com.alekseenko.lms.validator.impl;

import com.alekseenko.lms.validator.TitleValidator;
import com.alekseenko.lms.validator.chain.EnglishCyrillicCharactersCheck;
import com.alekseenko.lms.validator.chain.SpaceCheck;
import com.alekseenko.lms.validator.chain.SpecialCharactersCheck;
import com.alekseenko.lms.validator.chain.TitleChecker;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnyTitleValidator implements TitleValidator {

  Logger logger = LoggerFactory.getLogger(AnyTitleValidator.class);

  protected TitleChecker titleChecker = new SpecialCharactersCheck();

  protected TitleChecker commonChain() {
    return titleChecker.linkWith(new SpaceCheck())
        .linkWith(new EnglishCyrillicCharactersCheck());
  }

  @Override
  public boolean isValid(List<String> wordTitle) {
    logger.debug("AnyTitleValidator entered");
    commonChain();
    return titleChecker.check(wordTitle);
  }
}
