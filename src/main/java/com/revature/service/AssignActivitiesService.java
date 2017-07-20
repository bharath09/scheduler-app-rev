package com.revature.service;

import static com.revature.utils.Constants.UTC;
import static java.sql.Timestamp.valueOf;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.revature.utils.CalendarUtils;

@Service
public class AssignActivitiesService implements Serializable {
  private static Logger logger = Logger.getLogger(AssignActivitiesService.class);
  private static final long serialVersionUID = 1L;

  @Autowired
  private AssignActivitiesDAO activitiesDAO;
  @Autowired
  private ApplicationUtils applicationUtils;

  public void scheduleEmailJob(String emailJobId) throws Exception {
    AssignedInternActivityScheduler data = activitiesDAO.getSchedulerByJobId(emailJobId);
    scheduleEmailJob(data);
  }

  private void scheduleEmailJob(AssignedInternActivityScheduler data) {
    try {
      if (data != null && "scheduled".equals(data.getEmailJobStatus())
          && ("assign_now".equals(data.getStatus())
              || !valueOf(LocalDateTime.now(ZoneId.of(UTC))).after(data.getEndTime()))) {
        Scheduler s = applicationUtils.scheduler;
        JobDetail job = JobBuilder.newJob(AssignedItemEmailTriggerJob.class)
            .withIdentity("jobKey", data.getEmailJobId()).build();
        JobDataMap jobDataMap = job.getJobDataMap();
        jobDataMap.put("source", data);

        Trigger trigger = TriggerBuilder.newTrigger().forJob(job)
            .startAt(CalendarUtils.convertToSpecificTimeZone(data.getEmailTriggerTime(), UTC,
                ZoneId.systemDefault().toString()).orElse(null))
            .build();
        s.scheduleJob(job, trigger);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  public void schedulePendingEmailJobs() {
    try {
      List<AssignedInternActivityScheduler> data = activitiesDAO.getPendingEmailJobs();
      data.stream().forEach(this::scheduleEmailJob);
    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
    }
  }

}
