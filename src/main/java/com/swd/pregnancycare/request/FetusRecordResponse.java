package com.swd.pregnancycare.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FetusRecordResponse {
    Integer fetusWeek;  // Thay thế dateRecord bằng fetusWeek
    BigDecimal weight;
    BigDecimal height;

}
