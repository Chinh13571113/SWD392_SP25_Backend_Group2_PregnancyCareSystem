package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.AdviceDTO;

import java.util.List;

public interface AdviceServices {
  void saveAdvice(int fetusId, String title, String description );
  List<AdviceDTO> getAllAdvices();
  void deleteAdvice(int id);
  void updateAdvice(int id, String answer, boolean status);
}
