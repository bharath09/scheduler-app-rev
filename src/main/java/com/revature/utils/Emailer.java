package com.revature.utils;

import java.io.StringWriter;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.revature.models.EmailWrapper;
import com.revature.models.MailDTO;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Emailer implements Runnable {

  private JavaMailSenderImpl sender;

  private EmailWrapper email;

  private MailDTO mailDTO;

  private Logger logger = Logger.getLogger(Emailer.class);

  public Emailer() {
    super();
  }

  public Emailer(JavaMailSenderImpl javaMailSenderImpl, EmailWrapper email, MailDTO mailDTO) {
    this.sender = javaMailSenderImpl;
    this.email = email;
    this.mailDTO = mailDTO;
  }

  @Override
  public void run() {
    sendEmail();

  }

  public void sendEmail() {
    try {
      MimeMessage msg = sender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

      if (StringUtils.isBlank(email.getContent())) {
        Configuration configuration = mailDTO.getFreeMarkerConfiguration();
        Template template = configuration.getTemplate(email.getTemplateName());
        StringWriter out = new StringWriter();
        template.process(email.getTemplateVars(), out);
        helper.setText(out.toString(), true);
      } else {
        helper.setText(email.getContent(), true);
      }
      String mailFrom;
      if (mailDTO.getMailFrom() != null) {
        mailFrom = mailDTO.getMailFrom();
      } else {
        mailFrom = mailDTO.getUsername();
      }
      if (email.getFromAddress() != null) {
        helper.setFrom(email.getFromAddress());
      }

      if (email.getFromName() != null) {
        helper.setFrom(mailFrom, email.getFromName());
      } else {
        helper.setFrom(mailFrom);
      }

      if (CollectionUtils.isNotEmpty(email.getToAddress())) {
        String[] toAddress = new String[email.getToAddress().size()];
        int iterator = 0;
        for (String addr : email.getToAddress()) {
          toAddress[iterator] = addr;
          iterator++;
        }
        helper.setTo(toAddress);
        if (email.getToName() != null && toAddress.length == 1) {
          String to = toAddress[0];
          helper.setTo(new InternetAddress(to, email.getToName()));
        } else {
          helper.setTo(toAddress);
        }
      }
      if (CollectionUtils.isNotEmpty(email.getCcAddress())) {
        String[] ccAddress = new String[email.getCcAddress().size()];
        int iterator = 0;
        for (String addr : email.getCcAddress()) {
          ccAddress[iterator] = addr;
          iterator++;
        }
        helper.setCc(ccAddress);
        if (email.getCcName() != null && ccAddress.length == 1) {
          String cc = ccAddress[0];
          helper.setCc(new InternetAddress(cc, email.getCcName()));
        } else {
          helper.setCc(ccAddress);

        }
      }
      if (CollectionUtils.isNotEmpty(email.getBccAddress())) {
        String[] bccAddress = new String[email.getBccAddress().size()];
        int iterator = 0;
        for (String addr : email.getBccAddress()) {
          bccAddress[iterator] = addr;
          iterator++;
        }
        helper.setBcc(bccAddress);
      }
      helper.setSubject(email.getSubject());
      sender.send(msg);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

}
