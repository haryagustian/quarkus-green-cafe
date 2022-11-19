package org.green.cafe.models;
import lombok.Getter;
import lombok.Setter;
import org.green.cafe.models.bases.CreatedBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "last_education")
public class LastEducation extends CreatedBase {

  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "uuid")
  @Column(name = "id", length = 36, nullable = false)
  @Setter
  @Getter
  private String id;

  @Column(name = "name", length = 30)
  @Setter
  @Getter
  private String name;

  public LastEducation() {
    super();
  }
}
