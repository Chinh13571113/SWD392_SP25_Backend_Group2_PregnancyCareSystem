package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogCommentDTO;
import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.dto.GroupDTO;
import com.swd.pregnancycare.dto.UserDTO;
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
import com.swd.pregnancycare.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServicesImpl implements GroupServices {

  @Autowired
  private GroupRepo groupRepo;
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private UserGroupRepo userGroupRepo;
  @Autowired
  private UserServicesImp userServicesImp;

  // Helper method: Chuyển đổi UserEntity sang UserDTO
  private UserDTO convertUserEntityToDTO(UserEntity user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setEmail(user.getEmail());
    userDTO.setFullName(user.getFullName());
    userDTO.setRoles(user.getRole().getName());
    userDTO.setStatus(user.isStatus());
    return userDTO;
  }

  // Helper method: Chuyển đổi GroupEntity sang GroupDTO
  private GroupDTO convertGroupEntityToDTO(GroupEntity group) {
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setId(group.getId());
    groupDTO.setName(group.getName());
    groupDTO.setDescription(group.getDescription());
    groupDTO.setDate(group.getDate());
    groupDTO.setDeleted(group.getDeleted());
    groupDTO.setOwner(convertUserEntityToDTO(group.getOwner()));
    if (group.getUsers() != null) {
      List<UserDTO> userDTOs = group.getUsers().stream()
              .map(userGroup -> convertUserEntityToDTO(userGroup.getUser()))
              .collect(Collectors.toList());
      groupDTO.setUsers(userDTOs);
    }
    return groupDTO;
  }

  // Helper method: Chuyển đổi BlogCommentEntity sang BlogCommentDTO
  private BlogCommentDTO convertBlogCommentEntityToDTO(com.swd.pregnancycare.entity.BlogCommentEntity blogComment) {
    BlogCommentDTO blogCommentDTO = new BlogCommentDTO();
    blogCommentDTO.setId(blogComment.getId());
    blogCommentDTO.setDescription(blogComment.getDescription());
    blogCommentDTO.setDatePublish(blogComment.getDatePublish());
    return blogCommentDTO;
  }

  // Helper method: Chuyển đổi BlogEntity sang BlogDTO
  private BlogDTO convertBlogEntityToDTO(com.swd.pregnancycare.entity.BlogEntity blog) {
    BlogDTO blogDTO = new BlogDTO();
    blogDTO.setId(blog.getId());
    blogDTO.setTitle(blog.getTitle());
    blogDTO.setDescription(blog.getDescription());
    blogDTO.setDatePublish(blog.getDatePublish());
    blogDTO.setUser(convertUserEntityToDTO(blog.getUser()));
    if (blog.getBlogComments() != null) {
      List<BlogCommentDTO> blogCommentDTOs = blog.getBlogComments().stream()
              .map(this::convertBlogCommentEntityToDTO)
              .collect(Collectors.toList());
      blogDTO.setBlogComments(blogCommentDTOs);
    }
    return blogDTO;
  }

  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public GroupResponse saveGroup(GroupRequest groupRequest) {
    UserResponse userResponse = userServicesImp.getMyInfo();
    Optional<UserEntity> user = userRepo.findByEmailAndStatusTrue(userResponse.getEmail());
    Optional<GroupEntity> group = groupRepo.findByNameAndDeletedFalse(groupRequest.getName());

    if (user.isEmpty())
      throw new AppException(ErrorCode.USER_NOT_EXIST);
    if (group.isPresent())
      throw new AppException(ErrorCode.GROUP_EXIST);

    try {
      UserEntity userEntity = user.get();
      GroupEntity newGroup = new GroupEntity();
      newGroup.setName(groupRequest.getName());
      newGroup.setDescription(groupRequest.getDescription());
      newGroup.setOwner(userEntity);
      newGroup.setDate(LocalDateTime.now());
      newGroup.setDeleted(false);
      GroupEntity savedGroup = groupRepo.save(newGroup);

      // Thêm owner vào group tự động
      addMemberToGroup(savedGroup.getId());

      // Xây dựng response sử dụng các hàm helper
      GroupResponse response = new GroupResponse();
      response.setId(savedGroup.getId());
      response.setName(savedGroup.getName());
      response.setDescription(savedGroup.getDescription());
      response.setDate(savedGroup.getDate());
      response.setOwner(convertUserEntityToDTO(userEntity));
      List<UserDTO> users = new ArrayList<>();
      users.add(convertUserEntityToDTO(userEntity));
      response.setUsers(users);
      return response;
    } catch (Exception e) {
      throw new AppException(ErrorCode.GROUP_HAS_USER_ALREADY);
    }
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
  public List<GroupDTO> getAllGroups() {
    return groupRepo.findAll().stream()
            .filter(group -> Boolean.FALSE.equals(group.getDeleted()))
            .map(this::convertGroupEntityToDTO)
            .collect(Collectors.toList());
  }

  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public List<GroupDTO> getAllMyGroups() {
    UserResponse userResponse = userServicesImp.getMyInfo();
    int currentUserId = userResponse.getId();
    return getAllGroups().stream()
            .filter(groupDTO -> groupDTO.getUsers() != null &&
                    groupDTO.getUsers().stream().anyMatch(userDTO -> userDTO.getId() == currentUserId))
            .collect(Collectors.toList());
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
  public void deleteGroup(int id) {
    GroupEntity groupEntity = groupRepo.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));
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
  public void addMemberToGroup(int groupId) {
    UserResponse userResponse = userServicesImp.getMyInfo();
    UserEntity user = userRepo.findByEmailAndStatusTrue(userResponse.getEmail())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
    GroupEntity group = groupRepo.findByIdAndDeletedFalse(groupId)
            .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));
    Optional<UserGroupEntity> existingMembership = userGroupRepo.findByUserIdAndGroupId(user.getId(), groupId);
    if (existingMembership.isPresent()) {
      throw new AppException(ErrorCode.GROUP_HAS_USER_ALREADY);
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
    GroupEntity groupEntity = groupRepo.findByIdAndDeletedFalse(groupId)
            .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_EXIST));
    GroupResponse groupResponse = new GroupResponse();
    groupResponse.setId(groupEntity.getId());
    groupResponse.setName(groupEntity.getName());
    groupResponse.setDescription(groupEntity.getDescription());
    groupResponse.setDate(groupEntity.getDate());
    groupResponse.setOwner(convertUserEntityToDTO(groupEntity.getOwner()));
    List<UserDTO> userDTOs = groupEntity.getUsers().stream()
            .map(userGroup -> convertUserEntityToDTO(userGroup.getUser()))
            .collect(Collectors.toList());
    groupResponse.setUsers(userDTOs);
    List<BlogDTO> blogDTOs = groupEntity.getBlogs().stream()
            .filter(blogEntity -> blogEntity.getUser().getRole().getName().equals("MEMBER"))
            .map(this::convertBlogEntityToDTO)
            .collect(Collectors.toList());
    groupResponse.setBlogs(blogDTOs);
    return groupResponse;
  }
}
