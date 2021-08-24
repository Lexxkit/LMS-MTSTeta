package com.alekseenko.lms.validator;

import com.alekseenko.lms.validator.impl.AnyTitleValidator;
import com.alekseenko.lms.validator.impl.EmailValidator;
import com.alekseenko.lms.validator.impl.EnTitleValidator;
import com.alekseenko.lms.validator.impl.LoginValidator;
import com.alekseenko.lms.validator.impl.PasswordValidator;
import com.alekseenko.lms.validator.impl.PhoneValidator;
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

  @Override
  public void initialize(TitleCase constraintAnnotation) {
    TitleType type = constraintAnnotation.type();
    if (type == TitleType.RU) {
      titleValidator = new RuTitleValidatorImpl();
      logger.debug("Ru initialize");
    } else if (type == TitleType.EN) {
      titleValidator = new EnTitleValidator();
      logger.debug("En initialize");
    } else if (type == TitleType.ANY) {
      titleValidator = new AnyTitleValidator();
      logger.debug("Any initialize");
    } else if (type == TitleType.EMAIL) {
      titleValidator = new EmailValidator();
      logger.info("Email validation initialize");
    } else if (type == TitleType.LOGIN) {
      titleValidator = new LoginValidator();
      logger.info("Login validation initialize");
    } else if (type == TitleType.PASSWORD) {
      titleValidator = new PasswordValidator();
      logger.info("Password validation initialize");
    } else if (type == TitleType.PHONE) {
      titleValidator = new PhoneValidator();
      logger.info("Phone validation initialize");
    }
    else {
      logger.info("Could not find this type of validation");
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
