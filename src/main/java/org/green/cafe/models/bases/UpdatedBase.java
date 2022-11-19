package org.green.cafe.models.bases;

import lombok.Getter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class UpdatedBase extends CreatedBase{

  @Column(name = "updated_at")
  @UpdateTimestamp
  @Getter
  private LocalDateTime updatedAt;
}
