package org.green.cafe.models.bases;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
@MappedSuperclass
public class CreatedBase extends PanacheEntityBase {

  @Column(name = "created_at")
  @CreationTimestamp
  @Getter
  private LocalDateTime createdAt;

}
