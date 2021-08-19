package com.alekseenko.lms.validator;

import com.alekseenko.lms.validator.impl.AnyTitleValidator;
import com.alekseenko.lms.validator.impl.EnTitleValidator;
import com.alekseenko.lms.validator.impl.RuTitleValidatorImpl;
import com.alekseenko.lms.validator.type.TitleCase;
import com.alekseenko.lms.validator.type.TitleType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TitleCaseValidator implements ConstraintValidator<TitleCase, String> {

  Logger logger = LoggerFactory.getLogger(TitleCaseValidator.class);
  private TitleValidator titleValidator;
  private TitleType type;

  @Override
  public void initialize(TitleCase constraintAnnotation) {
    type = constraintAnnotation.type();
    if (type == TitleType.RU) {
      titleValidator = new RuTitleValidatorImpl();
      logger.debug("Ru initialize");
    } else if (type == TitleType.EN) {
      titleValidator = new EnTitleValidator();
      logger.debug("En initialize");
    } else {
      titleValidator = new AnyTitleValidator();
      logger.debug("Any initialize");
    }
  }

  @Override
  public boolean isValid(String title, ConstraintValidatorContext context) {
    final List<String> titleWords = Arrays.stream(title.split(" ")).collect(Collectors.toList());
    return validation(titleWords);
  }

  private boolean validation(List<String> titleWords) {
    logger.debug("titleWords = " + titleWords);
    return titleValidator.isValid(titleWords);
  }
}
