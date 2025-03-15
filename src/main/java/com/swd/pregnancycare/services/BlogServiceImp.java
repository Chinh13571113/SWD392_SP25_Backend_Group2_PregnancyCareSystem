package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogCommentDTO;
import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.dto.UserDTO;
import com.swd.pregnancycare.entity.*;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.repository.*;
import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.request.GroupRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.response.UserResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogServiceImp implements BlogServices {
  @Autowired
  private BlogRepo blogRepo;
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private UserServicesImp userServicesImp;
  @Autowired
  private GroupRepo groupRepo;
  @Autowired
  private BlogCategoryRepo blogCategoryRepo;

  @Override
  @PreAuthorize("hasAnyRole( 'MEMBER', 'EXPERT')")
  public void saveBlog(BlogRequest blogRequest) {
    UserResponse userResponse = userServicesImp.getMyInfo();
    Optional<UserEntity> user = userRepo.findByIdAndStatusTrue(userResponse.getId());
    UserEntity userEntity = user.get();

    GroupEntity groupEntity = groupRepo.findByIdAndDeletedFalse(blogRequest.getGroupId()).orElseThrow(()-> new AppException(ErrorCode.GROUP_NOT_EXIST));

    Optional<BlogCategoryEntity> blogCategory = blogCategoryRepo.findByIdAndDeletedFalse(blogRequest.getBlogCategoryId());
    BlogCategoryEntity blogCategoryEntity = blogCategory.get();

    BlogEntity newBlog = new BlogEntity();

    newBlog.setTitle(blogRequest.getTitle());
    newBlog.setDescription(blogRequest.getDescription());
    newBlog.setStatus(false);
    newBlog.setDatePublish(LocalDateTime.now());
    newBlog.setDeleted(false);
    newBlog.setGroup(groupEntity);
    newBlog.setUser(userEntity);
    newBlog.setBlogCategory(blogCategoryEntity);
    blogRepo.save(newBlog);
  }


  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER', 'EXPERT')")
  public List<BlogDTO> getAllBlogs() {
    return blogRepo.findAll().stream()
            .filter(blog -> Boolean.FALSE.equals(blog.getDeleted())) // Chỉ lấy những blog chưa bị xóa
            .map(data -> {
              BlogDTO blogDTO = new BlogDTO();
              blogDTO.setId(data.getId());
              blogDTO.setTitle(data.getTitle());
              blogDTO.setDescription(data.getDescription());
              blogDTO.setDatePublish(data.getDatePublish());
              blogDTO.setStatus(data.getStatus());
              blogDTO.setDeleted(data.getDeleted());

              UserDTO userDTO = new UserDTO();
              userDTO.setId(data.getUser().getId());
              userDTO.setEmail(data.getUser().getEmail());
              userDTO.setFullName(data.getUser().getFullName());
              userDTO.setRoles(data.getUser().getRole().getName());
              blogDTO.setUser(userDTO);

              if(data.getBlogComments() != null) {
                List<BlogCommentDTO> blogCommentDTOS = data.getBlogComments().stream().map(blogCommentEntity->{
                  BlogCommentDTO blogCommentDTO = new BlogCommentDTO();

                  blogCommentDTO.setId(blogCommentEntity.getId());
                  blogCommentDTO.setDescription(blogCommentEntity.getDescription());
                  blogCommentDTO.setDatePublish(blogCommentEntity.getDatePublish());

                  UserDTO userCommentDTO = new UserDTO();
                  userCommentDTO.setId(blogCommentEntity.getUser().getId());
                  userCommentDTO.setEmail(blogCommentEntity.getUser().getEmail());
                  userCommentDTO.setFullName(blogCommentEntity.getUser().getFullName());
                  userCommentDTO.setRoles(blogCommentEntity.getUser().getRole().getName());
                  userCommentDTO.setStatus(blogCommentEntity.getUser().isStatus());

                  blogCommentDTO.setUser(userCommentDTO);

                  return blogCommentDTO;
                }).collect(Collectors.toList());
                blogDTO.setBlogComments(blogCommentDTOS);
              }

              return blogDTO;
            })
            .toList();
  }




  @Override
  @PreAuthorize("hasAnyRole( 'MEMBER', 'EXPERT')")
  public void deleteBlog(int id) {
    Optional<BlogEntity> blog = blogRepo.findByIdAndDeletedFalse(id);
    if (blog.isEmpty()) throw new AppException(ErrorCode.BLOG_NOT_EXIST);
    BlogEntity blogEntity = blog.get();
    blogEntity.setStatus(true);
    blogRepo.save(blogEntity);
  }



  @Override
  @PreAuthorize("hasAnyRole( 'MEMBER', 'EXPERT')")
  public void updateBlog(BlogRequest blogRequest, int id) {
    BlogEntity blogEntity = blogRepo.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST));

    blogEntity.setTitle(blogRequest.getTitle());
    blogEntity.setDescription(blogRequest.getDescription());
    blogEntity.setDatePublish(LocalDateTime.now());

    blogRepo.save(blogEntity);
  }
}
