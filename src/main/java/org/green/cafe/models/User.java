package org.green.cafe.models;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User extends PanacheEntityBase {

  @Id
  public String id = UUID.randomUUID().toString();

  @NotNull
  @Column(name = "full_name")
  public String fullName;

  @NotNull
  @Column(name = "email", unique = true)
  public String email;

  @Column(name = "mobile_phone_number")
  public String mobilePhoneNumber;

  @Column(name = "work_phone_number")
  public String workPhoneNumber;

  @NotNull
  @Column(name = "login_name")
  public String loginName;

  @NotNull
  @Column(name = "password")
  public String password;

  @NotNull
  @Column(name = "address" , columnDefinition = "TEXT")
  public String address;

  @Column(name = "created_at")
  public Date createdAt;

  @Column(name = "updated_at")
  public Date updatedAt;


  public static Optional<User> findByUUID(String uuid){
    return find("id = ?1", uuid).firstResultOptional();
  }
  public static Optional<User> deleteByUUID(String uuid){
    return find("id = ?1", uuid).firstResultOptional();
  }

  public static Optional<User> findByLoginName(String loginName){
    return find("loginName = ?1", loginName).firstResultOptional();
  }
}
