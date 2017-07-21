package com.revature.jobs;

import static com.revature.utils.CalendarUtils.convertToSpecificTimeZone;
import static com.revature.utils.Constants.EMAIL_PATTERN;
import static com.revature.utils.Constants.UTC;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.revature.data.AssignActivitiesDAO;
import com.revature.models.AssignedInternActivityScheduler;
import com.revature.models.EmailWrapper;
import com.revature.utils.ApplicationContextUtils;
import com.revature.utils.ApplicationUtils;
import com.revature.utils.Emailer;

public class AssignedItemEmailTriggerJob1 implements Job {

  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      AssignedInternActivityScheduler data =
          (AssignedInternActivityScheduler) context.getMergedJobDataMap().get("source");

      ApplicationUtils applicationUtils = ApplicationContextUtils.getBean("applicationUtils");
      JavaMailSenderImpl javaMailSenderImpl = applicationUtils.javaMailSender;

      Timestamp timestamp =
          convertToSpecificTimeZone(data.getStartTime(), UTC, data.getTimeZone()).orElse(null);
      SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
      data.setStartTimeString(DATE_FORMAT.format(timestamp));
      if (data.getEndTime() != null) {
        timestamp =
            convertToSpecificTimeZone(data.getEndTime(), UTC, data.getTimeZone()).orElse(null);
        data.setStartTimeString(DATE_FORMAT.format(timestamp));
      }
      EmailWrapper emailWrapper = new EmailWrapper();
      List<String> toAddress = new ArrayList<>();
      List<String> ccAddress = new ArrayList<>();

      toAddress.add(data.getInternEmail());

      emailWrapper.setToAddress(toAddress);
      emailWrapper.setBccAddress(Stream.of(System.getenv("sink_mail").split(","))
          .filter(EMAIL_PATTERN.asPredicate()).collect(Collectors.toList()));

      emailWrapper.setCcAddress(ccAddress);
      emailWrapper.setToName(data.getInternName());
      emailWrapper.setFromName("RevaturePro");
      emailWrapper.setSubject("RevaturePro | An Assigned Activity Becomes Available Soon!");
      emailWrapper.setTemplateName("t079-scheduledContentNotification.ftl");


      Map<String, Object> templateVars = new HashMap<>();
      templateVars.put("subject", "RevaturePro | An Assigned Activity Becomes Available Soon!");
      templateVars.put("data", data);

      templateVars.put("regardsUrl", System.getenv("EMAIL_REGARDS_URL"));
      templateVars.put("year", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
      emailWrapper.setTemplateVars(templateVars);

      new Emailer(javaMailSenderImpl, emailWrapper, applicationUtils.maildto).run();
      AssignActivitiesDAO activitiesDAO = ApplicationContextUtils.getBean("assignActivitiesDAO");
      activitiesDAO.updateEmailJobStatus(data.getEmailJobId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
