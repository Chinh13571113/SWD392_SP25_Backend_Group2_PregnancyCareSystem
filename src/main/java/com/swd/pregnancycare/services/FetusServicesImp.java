package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.entity.FetusEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.FetusRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.FetusRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FetusServicesImp implements FetusServices {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private FetusRepo fetusRepo;

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public List<FetusDTO> getAllFetus() {
    return fetusRepo.findAll().stream().map(data -> {
      FetusDTO fetusDTO = new FetusDTO();
      fetusDTO.setIdFetus(data.getId());
      fetusDTO.setNameFetus(data.getName());
      fetusDTO.setDateFetus(data.getDueDate());
      fetusDTO.setGenderFetus(data.getGender());
      return fetusDTO;
    }).toList();
  }

  @Transactional
  @Override
  public void saveFetus(FetusRequest fetusRequest) {
    Optional<UserEntity> user = userRepo.findByEmail(fetusRequest.getEmail());
    // Is user exist
    if (user.isEmpty()) throw new AppException(ErrorCode.USER_NOT_EXIST);

    UserEntity userEntity = user.get();
    FetusEntity newFetus = new FetusEntity();

    newFetus.setName(fetusRequest.getName());
    newFetus.setGender(fetusRequest.getGender());
    newFetus.setDueDate(LocalDateTime.now());
    newFetus.setStatus(false);
    newFetus.setUser(userEntity);
    fetusRepo.save(newFetus);
  }

  @Transactional
  @Override
  public void deleteFetus(int id) {
      Optional<FetusEntity> fetus = fetusRepo.findById(id);
      if (fetus.isEmpty()) throw new AppException(ErrorCode.FETUS_NOT_EXIST);
      fetusRepo.deleteById(fetus.get().getId());
  }

  @Transactional
  @Override
  public void updateFetus(FetusRequest fetusRequest, int id) {
    FetusEntity fetusEntity = fetusRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FETUS_NOT_EXIST));
    fetusEntity.setDueDate(fetusRequest.getDueDate());
    fetusEntity.setName(fetusRequest.getName());
    fetusEntity.setGender(fetusRequest.getGender());
    fetusRepo.save(fetusEntity);
  }

}
