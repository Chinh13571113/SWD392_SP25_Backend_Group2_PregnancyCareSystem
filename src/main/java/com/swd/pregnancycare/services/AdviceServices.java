package com.swd.pregnancycare.services;

import com.swd.pregnancycare.request.AdviceRequest;
import com.swd.pregnancycare.response.AdviceResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface AdviceServices {
  void saveAdvice(AdviceRequest adviceRequest);

  // Function cho Expert: trả về toàn bộ advice
  List<AdviceResponse> getAllAdvicesForExpert();

  // Function cho Member: chỉ lấy những advice của member hiện tại
  List<AdviceResponse> getAllAdvicesForMember();

  void deleteAdvice(int id);

  void answerAdvice(int adviceId, String answer);
}
