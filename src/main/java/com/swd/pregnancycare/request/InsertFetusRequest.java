package com.swd.pregnancycare.request;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class InsertFetusRequest {
    private String name;
    private LocalDateTime dueDate;
    private String gender;
}
