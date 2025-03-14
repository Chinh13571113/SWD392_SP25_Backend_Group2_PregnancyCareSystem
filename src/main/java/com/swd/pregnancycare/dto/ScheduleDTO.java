package com.swd.pregnancycare.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ScheduleDTO {
    int id;
    int appointmentId;
    String notify;
    LocalDateTime dateRemind;
    Boolean isNotify;
}
