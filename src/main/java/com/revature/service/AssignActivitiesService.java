package com.revature.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.data.AssignActivitiesDAO;
import com.revature.jobs.AssignedItemEmailTriggerJob;
import com.revature.models.AssignedInternActivityScheduler;
import com.revature.utils.ApplicationUtils;

@Service
public class AssignActivitiesService implements Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private AssignActivitiesDAO activitiesDAO;
  @Autowired
  private ApplicationUtils applicationUtils;

  public void scheduleEmailJob(String emailJobId) throws Exception {
    AssignedInternActivityScheduler data = activitiesDAO.getSchedulerByJobId(emailJobId);
    if (data != null
        && ("assign_now".equals(data.getStatus()) || !new Date().after(data.getStartTime()))) {
      Scheduler s = applicationUtils.scheduler;
      JobDetail job = JobBuilder.newJob(AssignedItemEmailTriggerJob.class)
          .withIdentity("jobKey", emailJobId).build();
      JobDataMap jobDataMap = job.getJobDataMap();
      jobDataMap.put("source", data);

      ZonedDateTime utcTime = ZonedDateTime.of(
          new Timestamp(data.getEmailTriggerTime().getTime()).toLocalDateTime(), ZoneId.of("UTC"));
      Date triggerTime =
          Timestamp.valueOf(utcTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());

      Trigger trigger = TriggerBuilder.newTrigger().forJob(job).startAt(triggerTime).build();
      s.scheduleJob(job, trigger);
    }
  }
}
