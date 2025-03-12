package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.dto.FetusRecodDTO;
import com.swd.pregnancycare.entity.FetusEntity;
import com.swd.pregnancycare.entity.FetusRecordEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.mapper.FetusMapper;
import com.swd.pregnancycare.repository.FetusRecordRepo;
import com.swd.pregnancycare.repository.FetusRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.FetusRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FetusServicesImp implements FetusServices {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private FetusRepo fetusRepo;
  @Autowired
  private FetusRecordRepo fetusRecordRepo;

  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public List<FetusDTO> getAllFetus() {

    return fetusRepo.findAll().stream().map(data -> {
      FetusDTO fetusDTO = new FetusDTO();
      fetusDTO.setId(data.getId());
      fetusDTO.setName(data.getName());
      fetusDTO.setDueDate(data.getDueDate());
      fetusDTO.setGender(data.getGender());
      return fetusDTO;
    }).toList();
  }

  @Transactional
  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public FetusDTO saveFetus(FetusRequest fetusRequest) {
    Optional<UserEntity> user = userRepo.findByEmail(fetusRequest.getEmail());
    // Is user exist
    if (user.isEmpty()) throw new AppException(ErrorCode.USER_NOT_EXIST);

    UserEntity userEntity = user.get();
    FetusEntity newFetus = new FetusEntity();

    newFetus.setName(fetusRequest.getName());
    newFetus.setGender(fetusRequest.getGender());
    newFetus.setDueDate(fetusRequest.getDueDate());
    newFetus.setStatus(false);
    newFetus.setUser(userEntity);
    FetusEntity savedFetus = fetusRepo.save(newFetus);

    FetusDTO fetusDTO = new FetusDTO();
    fetusDTO.setId(savedFetus.getId());
    fetusDTO.setName(savedFetus.getName());
    fetusDTO.setGender(savedFetus.getGender());
    fetusDTO.setDueDate(savedFetus.getDueDate());

    return fetusDTO;
  }

  @Transactional
  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void deleteFetus(int id) {
      Optional<FetusEntity> fetus = fetusRepo.findById(id);
      if (fetus.isEmpty()) throw new AppException(ErrorCode.FETUS_NOT_EXIST);
      fetusRepo.deleteById(fetus.get().getId());
  }

  @Transactional
  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void updateFetus(FetusRequest fetusRequest, int id) {
    FetusEntity fetusEntity = fetusRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.FETUS_NOT_EXIST));
    fetusEntity.setDueDate(fetusRequest.getDueDate());
    fetusEntity.setName(fetusRequest.getName());
    fetusEntity.setGender(fetusRequest.getGender());
    fetusRepo.save(fetusEntity);
  }

  @Override
  public List<FetusDTO> getMyFetus() {
    var context = SecurityContextHolder.getContext();
    String name = context.getAuthentication().getName();

    UserEntity user =userRepo.findByEmail(name).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));


    return FetusMapper.INSTANCE.toListFetusDTO(fetusRepo.findByUserId(user.getId()));
  }

  //FETUS RECORD
  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public List<FetusRecodDTO> getFetusRecordById(int id) {
    List<FetusRecordEntity> fetusRecords = fetusRecordRepo.findByFetusId(id)
            .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOT_EXIST));
    return FetusMapper.INSTANCE.toListFetusRecordDTO(fetusRecords);
  }

  @PreAuthorize("hasRole('MEMBER')")
  @Override
  public void saveFetusRecord(int id, FetusRecodDTO fetusRecodDTO) {
    FetusEntity fetusEntity = fetusRepo.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.FETUS_NOT_EXIST));
    FetusRecordEntity fetusRecordEntity= new FetusRecordEntity();
    fetusRecordEntity.setFetus(fetusEntity);
    fetusRecordEntity.setWeight(fetusRecodDTO.getWeight());
    fetusRecordEntity.setHeight(fetusRecodDTO.getHeight());
//    fetusRecordEntity.setDateRecord(fetusRecodDTO.getDateRecord());
    fetusRecordRepo.save(fetusRecordEntity);

  }
  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void deleteFetusRecord(int id) {
    Optional<FetusRecordEntity> fetusRecord = fetusRecordRepo.findById(id);
    if (fetusRecord.isEmpty()) throw new AppException(ErrorCode.RECORD_NOT_EXIST);
    fetusRecordRepo.deleteById(fetusRecord.get().getId());
  }

}
