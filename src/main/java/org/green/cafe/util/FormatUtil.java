package org.green.cafe.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil {
  public static Boolean isPassword(String password){
    Pattern pattern = Pattern.compile("^(?=[^A-Z]*[A-Z])(?=[^a-z]*[a-z])(?=\\D*\\d).{8,}$");
    Matcher matcher = pattern.matcher(password);
    return matcher.find();
  }

  public static Boolean isAlphabet(String fullName){
    Pattern pattern = Pattern.compile("^[A-Za-z\\s]+$");
    Matcher matcher = pattern.matcher(fullName);
    return matcher.find();
  }

  public static Boolean isEmail(String email){
    Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$");
    Matcher matcher = pattern.matcher(email);
    return matcher.find();
  }

  public static Boolean isPhoneNumber(String phoneNumber){
    Pattern pattern = Pattern.compile("^\\+?([ -]?\\d+)+|\\(\\d+\\)([ -]\\d+)$");
    Matcher matcher = pattern.matcher(phoneNumber);
    return matcher.find();
  }

  public static Boolean isGender(String gender){
    Pattern pattern = Pattern.compile("^(F|M)$");
    Matcher matcher = pattern.matcher(gender);
    return matcher.find();
  }

  public static Boolean isDateFormat(String dateFormat){
    Pattern pattern = Pattern.compile("^(\\d{2}-\\d{2}-\\d{4})$");
    Matcher matcher = pattern.matcher(dateFormat);
    return matcher.find();
  }
}
