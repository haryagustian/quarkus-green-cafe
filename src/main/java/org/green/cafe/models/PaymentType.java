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
@Table(name = "payment_type")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class PaymentType extends PanacheEntityBase {
  @Id
  public String id = UUID.randomUUID().toString();

  @Column(name = "name")
  public String name;

  @Column(name = "code")
  public String code;

  @Column(name = "created_by")
  public Date createdBy;
}
