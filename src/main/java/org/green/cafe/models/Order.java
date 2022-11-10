package org.green.cafe.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "order_list")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Order extends PanacheEntityBase {

  Logger logger = Logger.getLogger(Order.class);

  @Id
  public String id = UUID.randomUUID().toString();

  @Column(name = "code")
  public String code;

  @Column(name = "total")
  public Double total;

  @Column(name = "sub_total")
  public Double subTotal;

  @Column(name = "tax")
  public Double tax;

  @Column(name = "created_at")
  public Date createdAt;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = Employee.class)
  @JoinColumn(name = "cashier")
  public Employee employee;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = PaymentType.class)
  @JoinColumn(name = "payment_type_id")
  public PaymentType paymentType;
}
