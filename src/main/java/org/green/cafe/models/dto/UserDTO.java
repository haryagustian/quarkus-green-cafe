package org.green.cafe.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties
public class UserDTO {
  public UUID id;
  public String fullName, email, mobilePhoneNumber, workPhoneNumber, loginName, password, address;
  public Date createdAt,updatedAt;
}
