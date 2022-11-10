package org.green.cafe.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Employee extends PanacheEntityBase {
  @Id
  public String id = UUID.randomUUID().toString();

  @Column(name = "full_name")
  public String fullName;

  @Column(name = "gender")
  public String gender;

  @Column(name = "dob")
  public Date dob;

  @Column(name = "pob")
  public Date pob;

  @Column(name = "email")
  public String email;

  @Column(name = "mobile_phone_number")
  public String mobilePhoneNumber;

  @Column(name = "created_at")
  public Date createdAt;

  @Column(name = "updated_at")
  public Date updatedAt;

  @Column(name = "is_active")
  public Boolean isActive;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = User.class)
  @JoinColumns({
      @JoinColumn(name = "created_by", referencedColumnName = "created_at"),
      @JoinColumn(name = "updated_by", referencedColumnName = "updated_at")
  })
  public User user;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = LastEducation.class)
  @JoinColumn(name = "last_education_id")
  public LastEducation lastEducation;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = JobPosition.class)
  @JoinColumn(name = "job_position_id")
  public JobPosition jobPosition;
}
