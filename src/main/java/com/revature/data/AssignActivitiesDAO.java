package com.revature.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    PreparedStatement ps = con.prepareStatement(
        "select s.id,s.timer_status, i.FULL_NAME internName, i.EMAIL_ID internEmail,"
            + "a.TITLE asmtName, s.start_time startTime,s.end_time endTime,s.email_trigger_time emailTriggerTime "
            + "from assigned_intern_activity_scheduler s join interns i on i.id=s.intern_id "
            + "join assessments a on a.id=s.asmt_id where s.job_id_email=? limit 1");
    ps.setString(1, emailJobId);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      AssignedInternActivityScheduler s = new AssignedInternActivityScheduler();
      s.setId(rs.getLong("id"));
      s.setInternName(rs.getString("internName"));
      s.setInternEmail(rs.getString("internEmail"));
      s.setAsmtName(rs.getString("asmtName"));
      s.setStartTime(rs.getTimestamp("startTime"));
      s.setEndTime(rs.getTimestamp("endTime"));
      s.setEmailTriggerTime(rs.getTimestamp("emailTriggerTime"));
      s.setStatus(rs.getString("timer_status"));
      return s;
    }
    return null;
  }
}
