package com.swd.pregnancycare.services;

import com.swd.pregnancycare.entity.GroupEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.GroupRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.GroupRequest;
import com.swd.pregnancycare.response.BaseResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupServicesImpl implements GroupServices {
  @Autowired
  private GroupRepo groupRepo;
  @Autowired
  private UserRepo userRepo;

  @Transactional
  @Override
  public BaseResponse saveGroup(GroupRequest group) {
    BaseResponse baseResponse = new BaseResponse();
    Optional<UserEntity> user = userRepo.findByEmail(group.getOwner_email());

    if(user.isEmpty()) {
      throw new AppException(ErrorCode.USER_NOT_FOUND);
    }
      UserEntity userEntity = user.get();
      GroupEntity newGroup = new GroupEntity();

      newGroup.setName(group.getName());
      newGroup.setDescription(group.getDescription());
      newGroup.setUser(userEntity);
      groupRepo.save(newGroup);
      baseResponse.setCode(ErrorCode.SAVED_SUCCESSFULLY.getCode());
      baseResponse.setData("{}");
      baseResponse.setMessage(ErrorCode.SAVED_SUCCESSFULLY.getMessage());
      return baseResponse;
  }

  @Override
  public BaseResponse getAllGroups() {
    return null;
  }

  @Override
  public BaseResponse deleteGroup(int id) {
    return null;
  }
}
