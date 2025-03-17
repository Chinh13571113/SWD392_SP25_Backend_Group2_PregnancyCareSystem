package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.AdviceDTO;
import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.entity.AdviceEntity;
import com.swd.pregnancycare.entity.FetusEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.AdviceRepo;
import com.swd.pregnancycare.repository.FetusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdviceServiceImpl implements AdviceServices {
  @Autowired
  private AdviceRepo adviceRepo;
  @Autowired
  private FetusRepo fetusRepo;

  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void saveAdvice(int fetusId, String title, String description) {
    FetusEntity fetus = fetusRepo.findById(fetusId).orElseThrow(()-> new AppException(ErrorCode.FETUS_NOT_EXIST));
    try {
      AdviceEntity advice = new AdviceEntity();
      advice.setTitle(title);
      advice.setDescription(description);
      advice.setStatus(false);
      advice.setFetus(fetus);
      adviceRepo.save(advice);
    } catch (Exception e) {
      throw new AppException(ErrorCode.ADVICE_SAVED_EXCEPTION);
    }
  }

  @Override
  @PreAuthorize("hasAnyRole('MEMBER', 'EXPERT')")
  public List<AdviceDTO> getAllAdvices() {
    return adviceRepo.findAll().stream().map(data -> {
      AdviceDTO advice = new AdviceDTO();
      advice.setId(data.getId());
      advice.setTitle(data.getTitle());
      advice.setDescription(data.getDescription());
      advice.setStatus(data.isStatus());
      advice.setAnswer(data.getAnswer());

      FetusDTO fetus = new FetusDTO();
      fetus.setId(data.getFetus().getId());
      fetus.setName(data.getFetus().getName());
      fetus.setDueDate(data.getFetus().getDueDate());
      fetus.setGender(data.getFetus().getGender());

      advice.setFetus(fetus);
      return advice;
    }).toList();
  }

  @Override
  @PreAuthorize("hasRole('EXPERT')")
  public void deleteAdvice(int id) {
    AdviceEntity advice = adviceRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ADVICE_NOT_EXIST));
    try {
      adviceRepo.deleteById(id);
    } catch (AppException app) {
      throw new AppException(ErrorCode.ADVICE_DELETE_FAILED);
    }
  }

  @Override
  @PreAuthorize("hasRole('EXPERT')")
  public void updateAdvice(int id, String answer, boolean status) {
    AdviceEntity advice = adviceRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.ADVICE_NOT_EXIST));
    try {
      advice.setAnswer(answer);
      advice.setStatus(status);
      adviceRepo.save(advice);
    } catch (AppException app) {
      throw new AppException(ErrorCode.ADVICE_UPDATE_FAILED);
    }
  }

}
