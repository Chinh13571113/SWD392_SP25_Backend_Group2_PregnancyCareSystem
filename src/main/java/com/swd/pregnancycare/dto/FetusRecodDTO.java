package com.swd.pregnancycare.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class FetusRecodDTO {
    BigDecimal weight;
    BigDecimal height;
//    LocalDateTime dateRecord;



}
