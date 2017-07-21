package com.revature.jobs;

import java.net.URL;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AssignedItemEmailTriggerJob implements Job {
  private static Logger logger = Logger.getLogger(AssignedItemEmailTriggerJob.class);

  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      String url = (String) context.getMergedJobDataMap().get("url");
      URL trigger = new URL(url);
      trigger.openStream();
      logger
          .info(String.format("Triggering the job. Job key: %s", context.getJobDetail().getKey()));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

}
