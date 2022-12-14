package org.green.cafe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem extends PanacheEntityBase {

  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "uuid")
  @Column(name = "id", length = 36, nullable = false)
  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  @ManyToOne(targetEntity = Order.class)
  @JsonIgnore
  @JoinColumn(name = "order_id")
  private Order orderId;

  @Getter
  @Setter
  @JsonIgnore
  @ManyToOne(targetEntity = Item.class)
  @JoinColumn(name = "item_id")
  private Item itemId;

  @Getter
  @Setter
  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Getter
  @Setter
  @Column(name = "price_total", nullable = false)
  private Double priceTotal;

  @Getter
  @Setter
  @Column(name = "note", columnDefinition = "text")
  private String note;

}
