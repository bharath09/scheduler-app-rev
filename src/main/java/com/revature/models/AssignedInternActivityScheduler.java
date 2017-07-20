package com.revature.models;

import java.io.Serializable;
import java.util.Date;


public class AssignedInternActivityScheduler implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String jobId;

  private String emailJobId;

  private Long internId;
  private String internName;
  private String internEmail;
  private String activityType;

  private Long activityId;
  private String activityName;

  private String status;

  private Date emailTriggerTime;

  private Date startTime;

  private Date endTime;

  private String jobStatus;

  private String emailJobStatus;

  private String timeZone;
  private String startTimeString;
  private String endTimeString;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public String getEmailJobId() {
    return emailJobId;
  }

  public void setEmailJobId(String emailJobId) {
    this.emailJobId = emailJobId;
  }

  public Long getInternId() {
    return internId;
  }

  public void setInternId(Long internId) {
    this.internId = internId;
  }



  public String getInternName() {
    return internName;
  }

  public void setInternName(String internName) {
    this.internName = internName;
  }

  public String getInternEmail() {
    return internEmail;
  }

  public void setInternEmail(String internEmail) {
    this.internEmail = internEmail;
  }

  public String getActivityType() {
    return activityType;
  }

  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getEmailTriggerTime() {
    return emailTriggerTime;
  }

  public void setEmailTriggerTime(Date emailTriggerTime) {
    this.emailTriggerTime = emailTriggerTime;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getJobStatus() {
    return jobStatus;
  }

  public void setJobStatus(String jobStatus) {
    this.jobStatus = jobStatus;
  }

  public String getEmailJobStatus() {
    return emailJobStatus;
  }

  public void setEmailJobStatus(String emailJobStatus) {
    this.emailJobStatus = emailJobStatus;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public String getStartTimeString() {
    return startTimeString;
  }

  public void setStartTimeString(String startTimeString) {
    this.startTimeString = startTimeString;
  }

  public String getEndTimeString() {
    return endTimeString;
  }

  public void setEndTimeString(String endTimeString) {
    this.endTimeString = endTimeString;
  }

}
