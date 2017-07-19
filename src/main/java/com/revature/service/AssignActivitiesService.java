package com.revature.service;

import java.io.Serializable;
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

  public void scheduleEmailJob(String emailJobId) throws Exception {
    AssignedInternActivityScheduler scheduler = activitiesDAO.getSchedulerByJobId(emailJobId);

    if ("assign_now".equals(scheduler.getStatus()) || !new Date().after(scheduler.getStartTime())) {
      Scheduler s = ApplicationUtils.scheduler;
      JobDetail job = JobBuilder.newJob(AssignedItemEmailTriggerJob.class)
          .withIdentity("jobKey", emailJobId).build();
      JobDataMap jobDataMap = job.getJobDataMap();
      jobDataMap.put("source", scheduler);
      Trigger trigger =
          TriggerBuilder.newTrigger().startAt(scheduler.getEmailTriggerTime()).build();
      s.scheduleJob(job, trigger);
    }
  }
}
