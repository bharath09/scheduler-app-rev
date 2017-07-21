package com.revature.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.AssignedInternActivityScheduler;

@Repository
public class AssignActivitiesDAO implements Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private DataSource dataSource;

  public AssignedInternActivityScheduler getSchedulerByJobId(String emailJobId) throws Exception {
    Connection con = dataSource.getConnection();
    PreparedStatement ps =
        con.prepareStatement("select s.id,s.timer_status, i.FULL_NAME internName,s.job_id_email,"
            + " i.EMAIL_ID internEmail,i.INTERN_TIMEZONE,s.email_job_status, "
            + "a.TITLE asmtName, s.start_time startTime,s.end_time endTime,s.email_trigger_time emailTriggerTime "
            + "from assigned_intern_activity_scheduler s join interns i on i.id=s.intern_id "
            + "join assessments a on a.id=s.asmt_id where s.job_id_email=? and s.email_job_status='scheduled' limit 1");
    ps.setString(1, emailJobId);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      AssignedInternActivityScheduler s = new AssignedInternActivityScheduler();
      s.setId(rs.getLong("id"));
      s.setActivityType("Quiz");
      s.setInternName(rs.getString("internName"));
      s.setInternEmail(rs.getString("internEmail"));
      s.setActivityName(rs.getString("asmtName"));
      s.setStartTime(getDate(rs.getString("startTime")));
      s.setEndTime(getDate(rs.getString("endTime")));
      s.setEmailTriggerTime(getDate(rs.getString("emailTriggerTime")));
      s.setTimeZone(rs.getString("INTERN_TIMEZONE"));

      s.setStatus(rs.getString("timer_status"));
      s.setEmailJobStatus(rs.getString("email_job_status"));
      s.setEmailJobId(rs.getString("job_id_email"));
      return s;
    }
    return null;
  }

  public void updateEmailJobStatus(String emailJobId) throws SQLException {
    Connection con = dataSource.getConnection();
    PreparedStatement ps = con.prepareStatement(
        "update assigned_intern_activity_scheduler s set s.email_job_status='done' where s.job_id_email=?");
    ps.setString(1, emailJobId);
    ps.executeUpdate();
  }

  public List<AssignedInternActivityScheduler> getPendingEmailJobs() throws Exception {
    List<AssignedInternActivityScheduler> datas = new ArrayList<>();
    Connection con = dataSource.getConnection();
    PreparedStatement ps =
        con.prepareStatement("select s.id,s.timer_status, i.FULL_NAME internName, s.job_id_email,"
            + "i.EMAIL_ID internEmail,i.INTERN_TIMEZONE,s.email_job_status, "
            + "a.TITLE asmtName, s.start_time startTime,s.end_time endTime,s.email_trigger_time emailTriggerTime "
            + "from assigned_intern_activity_scheduler s join interns i on i.id=s.intern_id "
            + "join assessments a on a.id=s.asmt_id where s.email_job_status='scheduled' and(s.timer_status='assign_now' or s.end_time>UTC_TIMESTAMP()) ");
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      AssignedInternActivityScheduler s = new AssignedInternActivityScheduler();
      s.setId(rs.getLong("id"));
      s.setActivityType("Quiz");
      s.setInternName(rs.getString("internName"));
      s.setInternEmail(rs.getString("internEmail"));
      s.setActivityName(rs.getString("asmtName"));
      s.setStartTime(getDate(rs.getString("startTime")));
      s.setEndTime(getDate(rs.getString("endTime")));
      s.setEmailTriggerTime(getDate(rs.getString("emailTriggerTime")));
      s.setTimeZone(rs.getString("INTERN_TIMEZONE"));
      s.setStatus(rs.getString("timer_status"));
      s.setEmailJobStatus(rs.getString("email_job_status"));
      s.setEmailJobId(rs.getString("job_id_email"));
      datas.add(s);
    }
    return datas;
  }

  public Date getDate(String dateString) throws ParseException {
    if (dateString != null) {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return formatter.parse(dateString);
    }
    return null;
  }
}
