package org.green.cafe.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "job_position")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class JobPosition extends PanacheEntityBase {
  @Id
  public String id = UUID.randomUUID().toString();

  @Column(name = "title")
  public String title;

  @Column(name = "salary")
  public Double salary;

  @Column(name = "created_at")
  public Date createdAt;
}
