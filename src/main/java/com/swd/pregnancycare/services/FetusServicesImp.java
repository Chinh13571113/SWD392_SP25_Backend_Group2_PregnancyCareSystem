package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.dto.FetusRecodDTO;
import com.swd.pregnancycare.entity.FetusEntity;
import com.swd.pregnancycare.entity.FetusRecordEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.entity.WhoStandardEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.mapper.FetusMapper;
import com.swd.pregnancycare.repository.FetusRecordRepo;
import com.swd.pregnancycare.repository.FetusRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.repository.WhoStandardRepo;
import com.swd.pregnancycare.request.FetusRecordResponse;
import com.swd.pregnancycare.request.FetusRequest;
import com.swd.pregnancycare.response.UserResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FetusServicesImp implements FetusServices {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private FetusRepo fetusRepo;
  @Autowired
  private FetusRecordRepo fetusRecordRepo;
  @Autowired
  private LoginServices loginServices;
  @Autowired
  private UserServicesImp userServicesImp;
  @Autowired
  private WhoStandardRepo whoStandardRepo;


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

  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public FetusDTO saveFetus(FetusRequest fetusRequest) {
    UserResponse userResponse = userServicesImp.getMyInfo();
    Optional<UserEntity> userEntity = userRepo.findByIdAndStatusTrue(userResponse.getId());
    UserEntity user = userEntity.get();
    LocalDate today = LocalDate.now();


    LocalDate dueDate = fetusRequest.getDueDate().toLocalDate();

    long weeks = ChronoUnit.WEEKS.between(today, dueDate);

    if (weeks > 42) {
      throw new AppException(ErrorCode.FETUS_IN_DANGER);
    }
    FetusEntity newFetus = new FetusEntity();

    newFetus.setName(fetusRequest.getName());
    newFetus.setGender(fetusRequest.getGender());
    newFetus.setDueDate(fetusRequest.getDueDate());
    newFetus.setStatus(false);
    newFetus.setUser(user);
    FetusEntity savedFetus = fetusRepo.save(newFetus);

    FetusDTO fetusDTO = new FetusDTO();
    fetusDTO.setId(savedFetus.getId());
    fetusDTO.setName(savedFetus.getName());
    fetusDTO.setGender(savedFetus.getGender());
    fetusDTO.setDueDate(savedFetus.getDueDate());

    return fetusDTO;
  }

  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void deleteFetus(int id) {
      Optional<FetusEntity> fetus = fetusRepo.findById(id);
      if (fetus.isEmpty()) throw new AppException(ErrorCode.FETUS_NOT_EXIST);
      fetusRepo.deleteById(fetus.get().getId());
  }

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
  @PreAuthorize("hasRole('MEMBER')")
  public List<FetusDTO> getMyFetus() {
    UserEntity user = loginServices.getUser();
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

  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public Map<String, List<?>> getStatisticFetusRecordById(int id) {
    List<FetusRecordEntity> fetusRecords = fetusRecordRepo.findByFetusId(id)
            .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOT_EXIST));

    // Lấy ngày dự sinh từ FetusEntity
    FetusEntity fetus = fetusRepo.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOT_EXIST));
    LocalDate dueDate = fetus.getDueDate().toLocalDate(); // Ngày dự sinh

    // Sử dụng TreeMap để lưu bản ghi mới nhất theo tuần (TreeMap giúp tự động sắp xếp theo tuần tăng dần)
    Map<Integer, FetusRecordEntity> latestRecordsByWeek = new TreeMap<>();

    for (FetusRecordEntity record : fetusRecords) {
      LocalDate recordDate = record.getDateRecord().toLocalDate();

      // Tính tuần thai
      int fetusWeek = 40 - (int) ChronoUnit.DAYS.between(recordDate, dueDate) / 7;

      // Nếu tuần đã có bản ghi, chỉ cập nhật nếu record mới hơn
      if (!latestRecordsByWeek.containsKey(fetusWeek) ||
              record.getDateRecord().isAfter(latestRecordsByWeek.get(fetusWeek).getDateRecord())) {
        latestRecordsByWeek.put(fetusWeek, record);
      }
    }

    // Chuyển dữ liệu thành danh sách
    List<Integer> fetusWeeks = new ArrayList<>(latestRecordsByWeek.keySet());
    List<BigDecimal> weights = latestRecordsByWeek.values().stream()
            .map(FetusRecordEntity::getWeight)
            .collect(Collectors.toList());
    List<BigDecimal> heights = latestRecordsByWeek.values().stream()
            .map(FetusRecordEntity::getHeight)
            .collect(Collectors.toList());

    // Tạo kết quả trả về
    Map<String, List<?>> response = new HashMap<>();
    response.put("fetusWeek", fetusWeeks);
    response.put("weight", weights);
    response.put("height", heights);

    return response;


  }


  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void saveFetusRecord(int id, FetusRecodDTO fetusRecodDTO) {
    FetusEntity fetusEntity = fetusRepo.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.FETUS_NOT_EXIST));
    FetusRecordEntity fetusRecordEntity= new FetusRecordEntity();
    fetusRecordEntity.setFetus(fetusEntity);
    fetusRecordEntity.setWeight(fetusRecodDTO.getWeight());
    fetusRecordEntity.setHeight(fetusRecodDTO.getHeight());
    fetusRecordEntity.setWarningMess(fetusRecodDTO.getWarningMess());
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

  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public int getFetusWeek(int id) {
    FetusEntity fetusEntity = fetusRepo.findById(id).orElseThrow(()->new AppException(ErrorCode.FETUS_NOT_EXIST));
    LocalDate dueDate  = fetusEntity.getDueDate().toLocalDate();
    LocalDate today = LocalDate.now();
    long daysUntilDueDate = ChronoUnit.DAYS.between(today, dueDate);

    // Tính số tuần còn lại
    int weeksUntilDueDate = (int) (daysUntilDueDate / 7);

    // Tính tuần thai hiện tại
    int currentWeek = 40 - weeksUntilDueDate;

    return Math.max(1, Math.min(40, currentWeek));
  }
  @PreAuthorize("hasRole('MEMBER')")
  @Override
  public Map<String, List<?>> getStandard() {
    List<WhoStandardEntity> standards = whoStandardRepo.findAll();

    List<Integer> fetusWeeks = new ArrayList<>();
    List<BigDecimal> weights = new ArrayList<>();
    List<BigDecimal> heights = new ArrayList<>();

    for (WhoStandardEntity standard : standards) {
      fetusWeeks.add(standard.getFetusWeek());
      weights.add(standard.getWeight());
      heights.add(standard.getHeight());
    }

    Map<String, List<?>> response = new HashMap<>();
    response.put("fetusWeek", fetusWeeks);
    response.put("weight", weights);
    response.put("height", heights);

    return response;


  }

}
