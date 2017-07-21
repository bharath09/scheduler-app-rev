package com.revature.utils;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.service.AssignActivitiesService;

@Component
public class SchedulerUtils {
  private static final Logger logger = Logger.getLogger(SchedulerUtils.class);
  @Autowired
  private AssignActivitiesService assignActivitiesService;

  @PostConstruct
  public void init() throws Exception {
    schedulePendingEmailJobs();
  }

  public void schedulePendingEmailJobs() {
    try {
      assignActivitiesService.schedulePendingEmailJobs();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }
}
