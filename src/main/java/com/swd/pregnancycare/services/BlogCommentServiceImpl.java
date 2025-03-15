package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.AdviceDTO;
import com.swd.pregnancycare.dto.BlogCommentDTO;
import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.dto.UserDTO;
import com.swd.pregnancycare.entity.BlogCommentEntity;
import com.swd.pregnancycare.entity.BlogEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.BlogCommentRepo;
import com.swd.pregnancycare.repository.BlogRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogCommentServiceImpl implements BlogCommentServices {
  @Autowired
  private BlogCommentRepo blogCommentRepo;
  @Autowired
  private UserServicesImp userServicesImp;
  @Autowired
  private BlogRepo blogRepo;
  @Autowired
  private UserRepo userRepo;



  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void saveBlogComment(int blogId, String description) {
    UserResponse user = userServicesImp.getMyInfo();
    BlogCommentEntity blogCommentEntity = new BlogCommentEntity();
    BlogEntity blogEntity = blogRepo.findByIdAndDeletedFalse(blogId).orElseThrow(()-> new AppException(ErrorCode.BLOG_NOT_EXIST));
    Optional<UserEntity> existUser = userRepo.findByIdAndStatusTrue(user.getId());
    UserEntity userEntity = existUser.get();

    try {
      blogCommentEntity.setDescription(description);
      blogCommentEntity.setDatePublish(LocalDateTime.now());
      blogCommentEntity.setUser(userEntity);
      blogCommentEntity.setBlog(blogEntity);

      blogCommentRepo.save(blogCommentEntity);
    } catch (Exception e) {
      throw new AppException(ErrorCode.BLOG_COMMENT_SAVED_EXCEPTION);
    }
  }

  @Override
  @PreAuthorize("hasAnyRole( 'MEMBER', 'ADMIN')")
  public void deleteBlogComment(int id) {
    blogCommentRepo.findById(id).orElseThrow(()-> new AppException(ErrorCode.BLOG_COMMENT_NOT_EXIST));
    blogCommentRepo.deleteById(id);
  }

  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public void updateBlogComment(int id, String description) {
    BlogCommentEntity blogCommentEntity = blogCommentRepo.findById(id).orElseThrow(()-> new AppException(ErrorCode.BLOG_COMMENT_NOT_EXIST));
    blogCommentEntity.setDescription(description);
    blogCommentRepo.save(blogCommentEntity);
  }


}
