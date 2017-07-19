package com.revature.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class AssignedItemEmailTriggerJob implements Job {

  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      System.out.println(context.getJobDetail().getKey());
    } catch (Exception e) {
    }
  }

}
