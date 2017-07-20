package com.revature.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.revature.models.AssignedInternActivityScheduler;
import com.revature.utils.ApplicationUtils;
import com.revature.utils.Emailer;

@Component
public class AssignedItemEmailTriggerJob implements Job {

  @Autowired
  private ApplicationUtils applicationUtils;


  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      AssignedInternActivityScheduler data =
          (AssignedInternActivityScheduler) context.getMergedJobDataMap().get("source");

      JavaMailSenderImpl javaMailSenderImpl = applicationUtils.javaMailSender;

      new Emailer(javaMailSenderImpl, null, applicationUtils.maildto);

    } catch (Exception e) {
    }
  }

}
