package com.revature.utils;

import static java.sql.Timestamp.valueOf;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import org.apache.log4j.Logger;

public class CalendarUtils {
  private static final Logger LOGGER = Logger.getLogger(CalendarUtils.class);

  public static Optional<Timestamp> convertToSpecificTimeZone(Date gnDate, String gnDateTimeZone,
      String convertToTimeZone) {
    Timestamp dateOut = null;
    try {
      if (gnDate != null && isNotBlank(gnDateTimeZone)) {

        ZonedDateTime x = ZonedDateTime.of(new Timestamp(gnDate.getTime()).toLocalDateTime(),
            ZoneId.of(gnDateTimeZone));
        dateOut = valueOf(x.withZoneSameInstant(ZoneId.of(convertToTimeZone)).toLocalDateTime());
      }
    } catch (Exception e) {
      LOGGER.error("Timezone conversion failed.", e);
    }
    return Optional.ofNullable(dateOut);
  }
}
