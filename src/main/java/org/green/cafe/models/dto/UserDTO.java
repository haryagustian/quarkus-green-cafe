package org.green.cafe.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties
public class UserDTO {
  public String id;
  public String fullName, email, mobilePhoneNumber, workPhoneNumber, loginName, password, address;
  public Date createdAt,updatedAt;
}
