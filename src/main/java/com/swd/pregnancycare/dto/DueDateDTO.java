package com.swd.pregnancycare.dto;

import com.swd.pregnancycare.enums.PregnancyPeriodEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DueDateDTO {
    PregnancyPeriodEntity dueDate;
    List<PregnancyPeriodEntity> timeline;
}
