package com.revature.utils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUtils {
  public static Scheduler scheduler;

  @PostConstruct
  public void init() throws Exception {
    scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.start();
  }

  @PreDestroy
  public void destroy() throws Exception {
    scheduler.shutdown();
  }

}
