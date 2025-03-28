package com.swd.pregnancycare.response;

import com.swd.pregnancycare.dto.CertificateDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpertResponse {
  int id;
  String fullName;
  String email;
  String description;
  String role;
  List<CertificateDTO> certificates;
}
