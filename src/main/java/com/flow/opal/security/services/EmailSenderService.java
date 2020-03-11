package com.flow.opal.security.services;

import com.flow.opal.models.entities.MyUser;
import com.flow.opal.security.models.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderService {

  private JavaMailSender javaMailSender;

  @Autowired
  public EmailSenderService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Async
  public void sendEmail(SimpleMailMessage email) {
    javaMailSender.send(email);
  }

  public SimpleMailMessage createEmail(ConfirmationToken confirmationToken, MyUser newUser) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(newUser.getEmail());
    mailMessage.setSubject("Complete Registration!");
    mailMessage.setFrom(System.getenv("MAIL_ADDRESS"));
    mailMessage.setText("To confirm your account, please click here : "
            + "http://localhost:5000/confirm-account?token=" + confirmationToken.getConfirmationToken());
    return mailMessage;
  }
}
