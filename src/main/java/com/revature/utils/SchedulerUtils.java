package com.revature.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.service.AssignActivitiesService;

@Component
public class SchedulerUtils {

  @Autowired
  private AssignActivitiesService assignActivitiesService;

  @PostConstruct
  public void init() throws Exception {
    schedulePendingEmailJobs();
  }

  public void schedulePendingEmailJobs() {
    assignActivitiesService.schedulePendingEmailJobs();
  }
}
