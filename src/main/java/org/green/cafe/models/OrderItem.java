package org.green.cafe.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_item")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderItem extends PanacheEntityBase {
  @Id
  public String id = UUID.randomUUID().toString();

  @Column(name = "quantity")
  public Integer quantity;

  @Column(name = "price_total")
  public Double priceTotal;

  @Column(name = "note", columnDefinition = "TEXT")
  public String note;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = Order.class)
  @JoinColumn(name = "order_id")
  public Order order;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = Item.class)
  @JoinColumn(name = "item_id")
  public Item item;
}
