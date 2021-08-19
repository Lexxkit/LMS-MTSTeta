package com.alekseenko.lms.validator.type;


import com.alekseenko.lms.validator.TitleCaseValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TitleCaseValidator.class)
public @interface TitleCase {

  TitleType type() default TitleType.ANY;
  String message() default "Input does not match heading rule";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
