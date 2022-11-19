package org.green.cafe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.green.cafe.models.bases.CreatedBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "order")
public class Order extends CreatedBase {

  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "uuid")
  @Column(name = "id", length = 36, nullable = false)
  @Getter
  @Setter
  private String id;


  @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "cashier")
  @JsonIgnore
  @Getter
  @Setter
  private Employee employee;

  @Getter
  @Setter
  @Column(name = "code", nullable = false)
  private String code;

  @Getter
  @Setter
  @Column(name = "total", nullable = false)
  private Double total;

  @Getter
  @Setter
  @Column(name = "sub_total", nullable = false)
  private Double subTotal;

  @Getter
  @Setter
  @Column(name = "tax", nullable = false)
  private Double tax;

  @Getter
  @Setter
  @ManyToOne(targetEntity = PaymentType.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "payment_type_id")
  private PaymentType paymentType;

  public Order() {
    super();
  }
}
