package org.green.cafe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.green.cafe.models.bases.UpdatedBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item extends UpdatedBase {

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
  @Column(name = "description", columnDefinition = "text", nullable = false)
  private String description;

  @Getter
  @Setter
  @Column(name = "category", nullable = false, length = 10)
  private String category;

  @Getter
  @Setter
  @Column(name = "price")
  private Double price;

  @Getter
  @Setter
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "created_by")
  @JsonIgnore
  private User createdBy;

  @Getter
  @Setter
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "updated_by")
  @JsonIgnore
  private User updatedBy;

  public Item() {
    super();
  }

  public static Long countByCategory(String category){
    if(category != null){
      return Item.count("category = ?1", category);
    }else {
      return Item.count();
    }
  }
}
