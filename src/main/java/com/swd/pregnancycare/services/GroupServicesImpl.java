package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.GroupDTO;
import com.swd.pregnancycare.entity.GroupEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.GroupRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServicesImpl implements GroupServices {
  @Autowired
  private GroupRepo groupRepo;
  @Autowired
  private UserRepo userRepo;

  @Override
  public void saveGroup(GroupRequest groupRequest) {
    Optional<UserEntity> user = userRepo.findByEmail(groupRequest.getOwner_email());
    Optional<GroupEntity> group = groupRepo.findByName(groupRequest.getName());

    if(user.isEmpty()) throw new AppException(ErrorCode.USER_NOT_EXIST);
    if(group.isPresent()) throw new AppException(ErrorCode.GROUP_EXIST);

    try {
      UserEntity userEntity = user.get();
      GroupEntity newGroup = new GroupEntity();

      newGroup.setName(groupRequest.getName());
      newGroup.setDescription(groupRequest.getDescription());
      newGroup.setUser(userEntity);
      newGroup.setDate(LocalDateTime.now());
      groupRepo.save(newGroup);
    } catch (Exception e) {
      throw new AppException(ErrorCode.GROUP_HAS_USER_ALREADY);
    }
  }

  @Override
  public List<GroupDTO> getAllGroups() {
    return groupRepo.findAll().stream().map(data -> {
      GroupDTO groupDTO = new GroupDTO();
      groupDTO.setId(data.getId());
      groupDTO.setName(data.getName());
      groupDTO.setDescription(data.getDescription());
      groupDTO.setDate(data.getDate());
      if (data.getUser() != null) {
        groupDTO.setUserId(data.getUser().getId());
      }
      return groupDTO;
    }).toList();
  }

  @Override
  public void deleteGroup(int id) {
    GroupEntity groupEntity = groupRepo.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));

    // Hủy liên kết từ phía User (vì User là inverse side)
    if (groupEntity.getUser() != null) {
      UserEntity owner = groupEntity.getUser();
      owner.setGroup(null);      // Hủy liên kết từ phía User
      groupEntity.setUser(null); // hủy liên kết từ phía Group (để rõ ràng)
    } else throw new AppException(ErrorCode.USER_NOT_EXIST);

    groupRepo.delete(groupEntity);
  }

  @Override
  public void updateGroup(GroupRequest groupRequest, int id) {
    GroupEntity groupEntity = groupRepo.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));

    groupEntity.setName(groupRequest.getName());
    groupEntity.setDescription(groupRequest.getDescription());
    groupEntity.setDate(LocalDateTime.now());

    groupRepo.save(groupEntity);
  }

}
