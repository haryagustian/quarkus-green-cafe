package org.green.cafe.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ItemCategory {
  A_LA_CARTE("A La Carte"),
  APPETIZERS("Appetizers"),
  SIDES("Sides"),
  EXTRAS("Extras"),
  BEVERAGES("Beverages");

  @Getter
  private String name;

}
