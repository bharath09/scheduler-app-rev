package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.service.AssignActivitiesService;

@RestController
public class AssignActivitiesController {

  @Autowired
  private AssignActivitiesService assignActivitiesService;

  @RequestMapping("/schedule_email")
  public void scheduleEmail(@RequestParam(value = "emailJobId") String emailJobId)
      throws Exception {

    assignActivitiesService.scheduleEmailJob(emailJobId);
  }
}
