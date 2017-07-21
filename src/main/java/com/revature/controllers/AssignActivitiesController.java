package com.revature.controllers;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.service.AssignActivitiesService;
import com.revature.utils.ApplicationUtils;

@RestController
public class AssignActivitiesController {

  @Autowired
  private AssignActivitiesService assignActivitiesService;

  @Autowired
  private ApplicationUtils applicationUtils;

  @RequestMapping("/schedule_email")
  public void scheduleEmail(@RequestParam(value = "emailJobId") String emailJobId)
      throws Exception {
    assignActivitiesService.scheduleEmailJob(emailJobId);
  }

  @RequestMapping("/scheduled_jobs")
  public Object scheduledJobs() throws Exception {
    Format formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm a z");
    Map<String, String> jobs = new HashMap<>();
    Scheduler scheduler = applicationUtils.scheduler;
    for (String groupName : scheduler.getJobGroupNames()) {
      for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
        String jobName = jobKey.getName();
        List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
        Date nextFireTime = triggers.get(0).getNextFireTime();
        jobs.put(jobName, formatter.format(nextFireTime));
      }
    }
    return jobs;
  }
}
