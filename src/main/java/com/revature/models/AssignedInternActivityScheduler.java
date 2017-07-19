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


  private Long asmtId;
  private String asmtName;

  private String status;

  private Date emailTriggerTime;

  private Date startTime;

  private Date endTime;

  private String jobStatus;

  private String emailJobStatus;

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

  public Long getAsmtId() {
    return asmtId;
  }

  public void setAsmtId(Long asmtId) {
    this.asmtId = asmtId;
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

  public String getAsmtName() {
    return asmtName;
  }

  public void setAsmtName(String asmtName) {
    this.asmtName = asmtName;
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


}
