package org.green.cafe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.green.cafe.models.bases.UpdatedBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "user")
public class User extends UpdatedBase {

  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "uuid")
  @Column(name = "id", length = 36, nullable = false)
  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  @Column(name = "full_name")
  private String fullName;

  @Getter
  @Setter
  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Getter
  @Setter
  @Column(name = "mobile_phone_number", length = 20, nullable = false)
  private String mobilePhoneNumber;

  @Getter
  @Setter
  @Column(name = "work_phone_number", length = 20, nullable = false)
  private String workPhoneNumber;

  @Getter
  @Setter
  @Column(name = "login_name", nullable = false)
  private String loginName;

  @Getter
  @Setter
  @Column(name = "password", nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Getter
  @Setter
  @Column(name = "address" , columnDefinition = "text", nullable = false)
  private String address;

  public User() {
    super();
  }

  public static Boolean isEmptyLoginName(String loginName){
    return find("login_name = ?1", loginName).firstResultOptional().isEmpty();
  }

  public static Optional<User> findByLoginName(String loginName){
    return find("login_name = ?1", loginName).firstResultOptional();
  }

}
