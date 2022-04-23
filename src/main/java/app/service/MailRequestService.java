package app.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Log4j2
@Service
@AllArgsConstructor
public class MailRequestService {
  private final MailSenderService mailSenderService;

  public void sendAdRequest(String fullName, String number, String time) {
    try {
      String receiver = "israfilzadehemin@gmail.com";
      String subject = "Alert! New Ad Request has been received";
      String body = String.format("Name and Surname: %s\n Mobile phone: %s\n Available time: %s", fullName, number, time);
      mailSenderService.send(receiver, subject, body);
      log.info("AD request has been sent successfully");
    } catch (MessagingException e) {
      log.warn("MessagingException happened: from MailRequestService.sendAdRequest()");
    }
  }

  public void sendFeedback(String email, String text) {
    try {
      String receiver = "israfilzadehemin@gmail.com";
      String subject = "Alert! New Feedback has been received";
      String body = String.format("Sender: %s\n Text: %s", email, text);
      mailSenderService.send(receiver, subject, body);
      log.info("Feedback has been sent successfully");
    } catch (MessagingException e) {
      log.warn("MessagingException happened: from MailRequestService.sendFeedback()");
    }
  }
}
