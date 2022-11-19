package org.green.cafe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.green.cafe.models.bases.UpdatedBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "employee")
public class Employee extends UpdatedBase {

  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "uuid")
  @Column(name = "id", length = 36, nullable = false)
  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  @Column(name = "full_name", length = 255)
  private String fullName;

  @Getter
  @Setter
  @Column(name = "gender", length = 10)
  private String gender;

  @Getter
  @Setter
  @Column(name = "dob")
  private Date dob;

  @Getter
  @Setter
  @Column(name = "pob", length = 55, nullable = false)
  private String pob;

  @Getter
  @Setter
  @Column(name = "email", length = 200, nullable = false)
  private String email;

  @Getter
  @Setter
  @Column(name = "mobile_phone_number",nullable = false, length = 20)
  private String mobilePhoneNumber;

  @Getter
  @Setter
  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @ManyToOne(targetEntity = JobPosition.class)
  @JoinColumn(name = "job_position_id", nullable = false)
  @JsonIgnore
  @Getter
  @Setter
  private JobPosition jobPosition;

  @ManyToOne(targetEntity = LastEducation.class)
  @JoinColumn(name = "last_education_id", nullable = false)
  @JsonIgnore
  @Getter
  @Setter
  private LastEducation lastEducation;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "created_by", nullable = false)
  @JsonIgnore
  @Getter
  @Setter
  private User createdBy;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "updated_by", nullable = false)
  @JsonIgnore
  @Getter
  @Setter
  private User updatedBy;

  public Employee() {
    super();
  }

  public static Optional<Employee> findByIdOptionalActive(String userUUID){
    return Employee.find("id = ?1 AND is_active = ?2",userUUID,true).firstResultOptional();
  }

  public static Long countByJobPosition(String jobPositionId){
    if(jobPositionId != null){
      return Employee.count("job_position_id = ?1", jobPositionId);
    }else {
      return Employee.count();
    }
  }

}

