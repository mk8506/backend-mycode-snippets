package kr.minj.helpers;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("mailhelper.sender.email") //can use helpers with @Autowired
public class MailHelper {
  @Autowired // gets JavaMailSender bean from springboot
  private final JavaMailSender javaMailSender = null;

  //sender information
  @Value(value = "${mailhelper.sender.name}") //assign default values to variables and method arguments
  private final String senderName = null;
  @Value(value = "${mailhelper.sender.email}")
  private final String senderEmail = null;
  
  /**
   * send email
   * @param receiverName
   * @param receiverEmail
   * @param subject(title)
   * @param content
   * @throws MessagingException 
   */
  public void sendMail(
    String receiverName,
    String receiverEmail,
    String subject,
    String content
  ) throws Exception {
    //send mail
    log.debug("------------------------------------------------------------");
    log.debug(String.format("RecvName: %s", receiverName));
    log.debug(String.format("RecvEmail: %s", receiverEmail));
    log.debug(String.format("Subject: %s", subject));
    log.debug(String.format("Content: %s", content));
    log.debug("------------------------------------------------------------");

    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    try {
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setTo(new InternetAddress(receiverEmail, receiverName, "UTF-8"));
        helper.setFrom(new InternetAddress(senderEmail, senderName, "UTF-8"));
        javaMailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
      log.error("setting failed", e);
      throw e;
      // response.setContentType("text/html; charset=euc-kr");
      // PrintWriter out = response.getWriter();
      // out.println("<script>");
      // out.println("history.back()");
      // out.println("</script>");
      // out.flush();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      log.error("incoding error", e);
      throw e;
      // response.setContentType("text/html; charset=euc-kr");
      // PrintWriter out = response.getWriter();
      // out.println("<script>");
      // out.println("history.back()");
      // out.println("</script>");
      // out.flush();
    } catch (Exception e) {
      log.error("unknown error", e);
      throw e;
    }
    // 메일 보내기
    javaMailSender.send(message);
  }
  
}
