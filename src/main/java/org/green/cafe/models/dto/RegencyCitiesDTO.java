package org.green.cafe.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RegencyCitiesDTO {
  String id,province_id,name;
}
