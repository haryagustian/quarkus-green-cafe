package org.green.cafe.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Gender {
  MALE("Male", "M"),
  FEMALE("Female", "F");

  @Getter
  private String name,code;

}
