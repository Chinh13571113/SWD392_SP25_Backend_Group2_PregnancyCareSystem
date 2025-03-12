package com.swd.pregnancycare.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FetusDTO {
    private int id;
    private String name;
    private LocalDateTime dueDate;
    private String gender;
}
