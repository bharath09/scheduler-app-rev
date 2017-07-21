package com.revature.utils;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.revature.data.EmailConfigDAO;
import com.revature.models.MailDTO;

import freemarker.template.Configuration;

@Component
public class ApplicationUtils {
  private static Logger logger = Logger.getLogger(ApplicationUtils.class);

  public Scheduler scheduler;
  public JavaMailSenderImpl javaMailSender;
  public MailDTO maildto;

  @Autowired
  private EmailConfigDAO emailConfigDAO;

  @Autowired
  private Configuration configuration;

  @Autowired
  private Environment environment;


  @PostConstruct
  public void init() throws Exception {
    scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.start();
    loadJavaMailSender();
  }

  @PreDestroy
  public void destroy() throws Exception {
    scheduler.shutdown();
  }

  public String getProperty(String key) {
    if (StringUtils.isNotBlank(key)) {
      return environment.getProperty(key);
    }
    return key;
  }

  public void loadJavaMailSender() {
    try {
      maildto = emailConfigDAO.getMailProperties();
      maildto.setFreeMarkerConfiguration(configuration);
      javaMailSender = new JavaMailSenderImpl();
      if (maildto.getMailHost() != null) {
        javaMailSender.setHost(maildto.getMailHost());
      }
      if (maildto.getMailPort() != 0) {
        javaMailSender.setPort(maildto.getMailPort());
      }
      if (maildto.getUsername() != null) {
        javaMailSender.setUsername(maildto.getUsername());
      }
      if (maildto.getPassword() != null) {
        javaMailSender.setPassword(maildto.getPassword());
      }
      if (maildto.isSmtpSsl()) {
        javaMailSender.setProtocol("smtps");
      }
      Properties javaMailProperties = null;
      if (maildto.isSmtpStartTls()) {
        if (javaMailProperties == null) {
          javaMailProperties = new Properties();
        }
        javaMailProperties.put("mail.smtp.starttls.enable", true);
      }
      if (maildto.isSmtpAuth()) {
        if (javaMailProperties == null) {
          javaMailProperties = new Properties();
        }
        javaMailProperties.put("mail.smtp.auth", true);
      }
      if (maildto.isQuitWait()) {
        if (javaMailProperties == null) {
          javaMailProperties = new Properties();
        }
        javaMailProperties.put("mail.smtp.quitwait", true);
      } else {
        if (javaMailProperties == null) {
          javaMailProperties = new Properties();
        }
        javaMailProperties.put("mail.smtp.quitwait", false);
      }
      if (javaMailProperties != null) {
        javaMailSender.setJavaMailProperties(javaMailProperties);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }


}
