package org.green.cafe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.green.cafe.models.bases.CreatedBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "order")
public class  Order extends CreatedBase {

  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "uuid")
  @Column(name = "id", length = 36, nullable = false)
  @Getter
  @Setter
  private String id;


  @ManyToOne(targetEntity = Employee.class)
  @JoinColumn(name = "cashier")
  @JsonIgnore
  @Getter
  @Setter
  private Employee cashier;

  @Getter
  @Setter
  @Column(name = "code")
  private String code;

  @Getter
  @Setter
  @Column(name = "total")
  private Double total;

  @Getter
  @Setter
  @Column(name = "sub_total")
  private Double subTotal;

  @Getter
  @Setter
  @Column(name = "tax")
  private Double tax;

  @Getter
  @Setter
  @ManyToOne(targetEntity = PaymentType.class)
  @JoinColumn(name = "payment_type_id")
  private PaymentType paymentTypeId;

  public Order() {
    super();
  }
}
