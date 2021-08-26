package com.alekseenko.lms.event;

import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.service.UserService;
import java.util.Locale;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RegistrationListener implements
    ApplicationListener<OnRegistrationCompleteEvent> {

  private final UserService service;

  private final MessageSource messages;

  private final JavaMailSender mailSender;

  @Value("${mail.host.address}")
  private static String HOST;

  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    this.confirmRegistration(event);
  }

  private void confirmRegistration(OnRegistrationCompleteEvent event) {
    User user = event.getUser();
    String token = UUID.randomUUID().toString();
    service.createVerificationToken(user, token);

    String recipientAddress = user.getEmail();
    String subject = "Registration Confirmation";
    String confirmationUrl
        = event.getAppUrl() + "/user/registrationConfirm?token=" + token;
    String message = messages.getMessage("message.regSucc", null, new Locale("ru"));
    log.info("Attempting to register a user '{}' with email address: {}", user.getUsername(),
        user.getEmail());

    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(recipientAddress);
    email.setSubject(subject);
    email.setText(message + "\r\n" + HOST + confirmationUrl);
    mailSender.send(email);
  }
}
