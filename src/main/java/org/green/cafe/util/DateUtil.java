package org.green.cafe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
  public static Date stringToDate(String input) throws ParseException {
    return new SimpleDateFormat("dd-mm-yy").parse(input);
  }
}
