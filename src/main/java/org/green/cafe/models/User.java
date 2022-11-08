package org.green.cafe.models;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.common.constraint.NotNull;
import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {


  @Id
  @GeneratedValue
  public UUID id;

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
  @Column(name = "address")
  public String address;

  @Column(name = "created_at")
  public Date createdAt;

  @Column(name = "updated_at")
  public Date updatedAt;

}
