package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.dto.GroupDTO;
import com.swd.pregnancycare.dto.UserDTO;
import com.swd.pregnancycare.entity.BlogEntity;
import com.swd.pregnancycare.entity.GroupEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.entity.UserGroupEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.GroupRepo;
import com.swd.pregnancycare.repository.UserGroupRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.GroupRequest;
import com.swd.pregnancycare.response.GroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
  @Autowired
  private UserGroupRepo userGroupRepo;



  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void saveGroup(GroupRequest groupRequest) {
    Optional<UserEntity> user = userRepo.findByEmailAndStatusTrue(groupRequest.getOwner_email());
    Optional<GroupEntity> group = groupRepo.findByNameAndDeletedFalse(groupRequest.getName());

    if(user.isEmpty()) throw new AppException(ErrorCode.USER_NOT_EXIST);
    if(group.isPresent()) throw new AppException(ErrorCode.GROUP_EXIST);

    try {
      UserEntity userEntity = user.get();
      GroupEntity newGroup = new GroupEntity();

      newGroup.setName(groupRequest.getName());
      newGroup.setDescription(groupRequest.getDescription());
      newGroup.setOwner(userEntity);
      newGroup.setDate(LocalDateTime.now());
      newGroup.setDeleted(false);
      groupRepo.save(newGroup);
    } catch (Exception e) {
      throw new AppException(ErrorCode.GROUP_SAVED_EXCEPTION);
    }
  }



  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
  public List<GroupDTO> getAllGroups() {
    return groupRepo.findAll().stream()
            .filter(group -> Boolean.FALSE.equals(group.getDeleted()))
            .map(data -> {
              GroupDTO groupDTO = new GroupDTO();
              groupDTO.setId(data.getId());
              groupDTO.setName(data.getName());
              groupDTO.setDescription(data.getDescription());
              groupDTO.setDate(data.getDate());
              groupDTO.setDeleted(data.getDeleted());

              // Chuyển đổi owner
              UserDTO ownerDTO = new UserDTO();
              ownerDTO.setId(data.getOwner().getId());
              ownerDTO.setEmail(data.getOwner().getEmail());
              ownerDTO.setFullName(data.getOwner().getFullName());
              ownerDTO.setRoles(data.getOwner().getRole().getName());
              ownerDTO.setStatus(data.getOwner().isStatus());
              groupDTO.setOwner(ownerDTO);

              // Chuyển đổi danh sách user thuộc user_group
              if (data.getUsers() != null) {
                List<UserDTO> userDTOs = data.getUsers().stream().map(userGroup -> {
                  UserEntity userEntity = userGroup.getUser();
                  UserDTO userDTO = new UserDTO();
                  userDTO.setId(userEntity.getId());
                  userDTO.setEmail(userEntity.getEmail());
                  userDTO.setFullName(userEntity.getFullName());
                  userDTO.setRoles(userEntity.getRole().getName());
                  userDTO.setStatus(userEntity.isStatus());
                  return userDTO;
                }).toList();
                groupDTO.setUsers(userDTOs);
              }
              return groupDTO;
            })
            .toList();
  }




  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
  public void deleteGroup(int id) {
    GroupEntity groupEntity = groupRepo.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));

//    // Hủy liên kết từ phía User (vì User là inverse side)
//    if (groupEntity.getUser() != null) {
//      UserEntity owner = groupEntity.getUser();
//      owner.setGroup(null);      // Hủy liên kết từ phía User
//      groupEntity.setUser(null); // hủy liên kết từ phía Group (để rõ ràng)
//    } else throw new AppException(ErrorCode.USER_NOT_EXIST);
    // groupRepo.delete(groupEntity);

    groupEntity.setDeleted(true);
    groupRepo.save(groupEntity);
  }



  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
  public void updateGroup(String name, String description, int id) {
    GroupEntity groupEntity = groupRepo.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));

    groupEntity.setName(name);
    groupEntity.setDescription(description);
    groupEntity.setDate(LocalDateTime.now());

    groupRepo.save(groupEntity);
  }

  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void addMemberToGroup(int groupId, String email) {
    UserEntity user = userRepo.findByEmailAndStatusTrue(email)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
    GroupEntity group = groupRepo.findByIdAndDeletedFalse(groupId)
            .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));

    Optional<UserGroupEntity> existingMembership = userGroupRepo.findByUserIdAndGroupId(user.getId(), groupId);
    if (existingMembership.isPresent()) {
      throw new AppException(ErrorCode.USER_ALREADY_IN_GROUP);
    }

    UserGroupEntity userGroup = new UserGroupEntity();
    userGroup.setUser(user);
    userGroup.setGroup(group);
    userGroup.setDeleted(false);

    userGroupRepo.save(userGroup);
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
  public GroupResponse getAllBlogsOfGroup(int groupId) {
    GroupEntity groupEntity = groupRepo.findByIdAndDeletedFalse(groupId).orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));
    GroupResponse groupResponse = new GroupResponse();

    groupResponse.setId(groupEntity.getId());
    groupResponse.setName(groupEntity.getName());
    groupResponse.setDescription(groupEntity.getDescription());
    groupResponse.setDate(groupEntity.getDate());

    // Owner
    UserDTO ownerDTO = new UserDTO();
    ownerDTO.setId(groupEntity.getOwner().getId());
    ownerDTO.setEmail(groupEntity.getOwner().getEmail());
    ownerDTO.setFullName(groupEntity.getOwner().getFullName());
    ownerDTO.setRoles(groupEntity.getOwner().getRole().getName());
    ownerDTO.setStatus(groupEntity.getOwner().isStatus());
    groupResponse.setOwner(ownerDTO);

    // Memeber thuộc group
    List<UserDTO> userDTOs = groupEntity.getUsers().stream().map(group -> {
      UserEntity userEntity = group.getUser();
      UserDTO userDTO = new UserDTO();
      userDTO.setId(userEntity.getId());
      userDTO.setEmail(userEntity.getEmail());
      userDTO.setFullName(userEntity.getFullName());
      userDTO.setRoles(userEntity.getRole().getName());
      userDTO.setStatus(userEntity.isStatus());
      return userDTO;
    }).toList();
    groupResponse.setUsers(userDTOs);

    // Map danh sách blog của group
    List<BlogDTO> blogDTOs = groupEntity.getBlogs().stream().map(blogEntity -> {
      BlogDTO blogDTO = new BlogDTO();
      blogDTO.setId(blogEntity.getId());
      blogDTO.setTitle(blogEntity.getTitle());
      blogDTO.setDescription(blogEntity.getDescription());
      blogDTO.setDatePublish(blogEntity.getDatePublish());

      // Nếu BlogDTO có thông tin Owner, map lại thông tin owner của blog
      UserDTO blogOwnerDTO = new UserDTO();
      blogOwnerDTO.setId(blogEntity.getUser().getId());
      blogOwnerDTO.setEmail(blogEntity.getUser().getEmail());
      blogOwnerDTO.setFullName(blogEntity.getUser().getFullName());
      blogOwnerDTO.setRoles(blogEntity.getUser().getRole().getName());
      blogOwnerDTO.setStatus(blogEntity.getUser().isStatus());
      blogDTO.setUser(blogOwnerDTO);

      return blogDTO;
    }).toList();
    groupResponse.setBlogs(blogDTOs);

    return groupResponse;
  }


}
