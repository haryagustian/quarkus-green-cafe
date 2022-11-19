package org.green.cafe.models;
import lombok.Getter;
import lombok.Setter;
import org.green.cafe.models.bases.CreatedBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "job_position")
public class JobPosition extends CreatedBase {

  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "uuid")
  @Column(name = "id", length = 36, nullable = false)
  @Setter
  @Getter
  private String id;

  @Column(name = "title", length = 30, nullable = false)
  @Setter
  @Getter
  private String title;

  @Column(name = "salary", nullable = false)
  @Setter
  @Getter
  private Double salary;

  public JobPosition() {
    super();
  }
}

