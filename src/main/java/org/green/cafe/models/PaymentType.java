package org.green.cafe.models;

import lombok.Getter;
import lombok.Setter;
import org.green.cafe.models.bases.CreatedBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "payment_type")
public class PaymentType extends CreatedBase {

  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "uuid")
  @Column(name = "id", length = 36, nullable = false)
  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  @Column(name = "name", nullable = false)
  private String name;

  @Getter
  @Setter
  @Column(name = "code", nullable = false)
  private String code;

  public PaymentType() {
    super();
  }
}
