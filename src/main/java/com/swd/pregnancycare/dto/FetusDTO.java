package com.swd.pregnancycare.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FetusDTO {
    private int idFetus;
    private String nameFetus;
    private LocalDateTime dateFetus;
    private String genderFetus;
}
