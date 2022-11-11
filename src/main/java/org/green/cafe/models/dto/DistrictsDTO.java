package org.green.cafe.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DistrictsDTO {
  String id,regency_id,name;
}
