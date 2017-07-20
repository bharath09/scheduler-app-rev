package com.revature.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.MailDTO;

@Repository
public class EmailConfigDAO implements Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private DataSource dataSource;


  public MailDTO getMailProperties() throws Exception {
    MailDTO mailDTO = new MailDTO();
    Connection con = dataSource.getConnection();
    PreparedStatement ps = con.prepareStatement(
        "select d.* from app_config ac join app_config_details d on d.APP_CONFIG_ID=ac.ID where ac.IS_ACTIVE=1");
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {

      if ("mail.host".equals(rs.getString("MODE_PROP"))) {
        mailDTO.setMailHost(rs.getString("MODE_VALUE"));
      } else if ("mail.port".equals(rs.getString("MODE_PROP"))) {
        String value = rs.getString("MODE_VALUE");
        mailDTO.setMailPort(value != null ? Integer.parseInt(value) : 0);
      } else if ("mail.from".equals(rs.getString("MODE_PROP"))) {
        String value = rs.getString("MODE_VALUE");
        mailDTO.setMailFrom(value);
      } else if ("mail.username".equals(rs.getString("MODE_PROP"))) {
        String value = rs.getString("MODE_VALUE");
        mailDTO.setUsername(value);
      } else if ("mail.password".equals(rs.getString("MODE_PROP"))) {
        String value = rs.getString("MODE_VALUE");
        mailDTO.setPassword(value);
      } else if ("mail.smtp.starttls".equals(rs.getString("MODE_PROP"))) {
        String value = rs.getString("MODE_VALUE");
        mailDTO.setSmtpStartTls((value != null ? Boolean.valueOf(value) : false));
      } else if ("mail.smtp.auth".equals(rs.getString("MODE_PROP"))) {
        String value = rs.getString("MODE_VALUE");
        mailDTO.setSmtpAuth((value != null ? Boolean.valueOf(value) : false));
      } else if ("mail.smtp.quit.wait".equals(rs.getString("MODE_PROP"))) {
        String value = rs.getString("MODE_VALUE");
        mailDTO.setQuitWait((value != null ? Boolean.valueOf(value) : false));
      } else if ("mail.mail.smtp.ssl".equals(rs.getString("MODE_PROP"))) {
        String value = rs.getString("MODE_VALUE");
        mailDTO.setSmtpSsl((value != null ? Boolean.valueOf(value) : false));
      }
    }
    con.close();
    return mailDTO;
  }
}
