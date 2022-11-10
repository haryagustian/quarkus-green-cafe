package org.green.cafe.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "item")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Item extends PanacheEntityBase {

  @Id
  public String id = UUID.randomUUID().toString();

  @Column(name = "name")
  public String name;

  @Column(name = "description", columnDefinition = "TEXT")
  public String description;

  @Column(name = "category")
  public String category;

  @Column(name = "price")
  public Double price;

  @Column(name = "created_at")
  public Date createdAt;

  @Column(name = "updated_at")
  public Date updatedAt;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, targetEntity = User.class)
  @JoinColumns({
      @JoinColumn(name = "created_by", referencedColumnName = "created_at"),
      @JoinColumn(name = "updated_by", referencedColumnName = "updated_at")
  })
  public User user;
}
