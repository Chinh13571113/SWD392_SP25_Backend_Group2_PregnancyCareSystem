package com.swd.pregnancycare.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
public class FetusRecodDTO {
    LocalDateTime dateRecord;
    BigDecimal weight;
    BigDecimal height;
    String warningMess;
//    LocalDateTime dateRecord;



}
