package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogCategoryDTO;
import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.dto.UserDTO;
import com.swd.pregnancycare.entity.AdviceEntity;
import com.swd.pregnancycare.entity.BlogCategoryEntity;
import com.swd.pregnancycare.entity.FetusEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.AdviceRepo;
import com.swd.pregnancycare.repository.BlogCategoryRepo;
import com.swd.pregnancycare.repository.FetusRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.AdviceRequest;
import com.swd.pregnancycare.response.AdviceResponse;
import com.swd.pregnancycare.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AdviceServiceImpl implements AdviceServices {
  @Autowired
  private AdviceRepo adviceRepo;
  @Autowired
  private FetusRepo fetusRepo;
  @Autowired
  private BlogCategoryRepo categoryRepo;
  @Autowired
  private UserServicesImp userServicesImp;
  @Autowired
  private UserRepo userRepo;


  // Function for MEMBER
  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void saveAdvice(AdviceRequest adviceRequest) {
    FetusEntity fetus = fetusRepo.findById(adviceRequest.getFetusId()).orElseThrow(()-> new AppException(ErrorCode.FETUS_NOT_EXIST));
    BlogCategoryEntity category = categoryRepo.findByIdAndDeletedFalse(adviceRequest.getCategoryId()).orElseThrow(()->new AppException(ErrorCode.CATEGORY_NOT_EXIST));
    try {
      AdviceEntity advice = new AdviceEntity();
      advice.setTitle(adviceRequest.getTitle());
      advice.setDescription(adviceRequest.getDescription());
      advice.setDatePublish(LocalDateTime.now());
      advice.setStatus(false);
      advice.setFetus(fetus);
      advice.setCategory(category);
      adviceRepo.save(advice);
    } catch (Exception e) {
      throw new AppException(ErrorCode.ADVICE_SAVED_EXCEPTION);
    }
  }

  private List<AdviceResponse> getAllAdvices() {
    return adviceRepo.findAll().stream().map(data -> {
      AdviceResponse advice = new AdviceResponse();
      advice.setId(data.getId());
      advice.setTitle(data.getTitle());
      advice.setDescription(data.getDescription());
      advice.setDatePublish(data.getDatePublish());
      advice.setStatus(data.isStatus());
      advice.setAnswer(data.getAnswer());
      advice.setAnswerDate(data.getAnswerDate());

      // Map Fetus
      FetusDTO fetus = new FetusDTO();
      fetus.setId(data.getFetus().getId());
      fetus.setName(data.getFetus().getName());
      fetus.setDueDate(data.getFetus().getDueDate());
      fetus.setGender(data.getFetus().getGender());
      advice.setFetus(fetus);

      // Map Expert
      if(data.getExpert() != null) {
        UserDTO expert = new UserDTO();
        expert.setId(data.getExpert().getId());
        expert.setFullName(data.getExpert().getFullName());
        expert.setEmail(data.getExpert().getEmail());
        expert.setRoles(data.getExpert().getRole().getName());
        expert.setStatus(data.getExpert().isStatus());
        advice.setExpert(expert);
      }

      // Map Member
      UserDTO member = new UserDTO();
      member.setId(data.getFetus().getUser().getId());
      member.setFullName(data.getFetus().getUser().getFullName());
      member.setEmail(data.getFetus().getUser().getEmail());
      member.setRoles(data.getFetus().getUser().getRole().getName());
      member.setStatus(data.getFetus().getUser().isStatus());
      advice.setMember(member);

      // Map Category
      BlogCategoryDTO category = new BlogCategoryDTO();
      category.setId(data.getCategory().getId());
      category.setName(data.getCategory().getName());
      category.setDescription(data.getCategory().getDescription());
      category.setDatePublish(data.getCategory().getDatePublish());
      category.setSlug(data.getCategory().getSlug());
      advice.setBlogCategory(category);

      return advice;
    }).toList();
  }


  // Function cho Member: chỉ lấy những advice của member hiện tại
  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public List<AdviceResponse> getAllAdvicesForMember() {
    // Lấy thông tin user hiện tại (member)
    UserResponse currentMember = userServicesImp.getMyInfo();
    // Lọc danh sách advice chỉ lấy những advice mà member của advice khớp với member hiện tại
    return getAllAdvices().stream()
            .filter(advice -> advice.getMember() != null
                    && advice.getMember().getId() == currentMember.getId())
            .collect(Collectors.toList());
  }



  // Function for EXPERT
  @Override
  @PreAuthorize("hasRole('EXPERT')")
  public List<AdviceResponse> getAllAdvicesForExpert() {
    // Gọi lại function gốc để lấy toàn bộ advice
    return getAllAdvices();
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
  public void answerAdvice(int adviceId, String answer) {
    UserResponse userResponse = userServicesImp.getMyInfo();
    Optional<UserEntity> existedExpert = userRepo.findByIdAndStatusTrue(userResponse.getId());
    UserEntity expert = existedExpert.get();
    AdviceEntity advice = adviceRepo.findById(adviceId).orElseThrow(() -> new AppException(ErrorCode.ADVICE_NOT_EXIST));
    try {
      advice.setAnswer(answer);
      advice.setStatus(true);
      advice.setAnswerDate(LocalDateTime.now());
      advice.setExpert(expert);
      adviceRepo.save(advice);
    } catch (AppException app) {
      throw new AppException(ErrorCode.ADVICE_UPDATE_FAILED);
    }
  }

}
