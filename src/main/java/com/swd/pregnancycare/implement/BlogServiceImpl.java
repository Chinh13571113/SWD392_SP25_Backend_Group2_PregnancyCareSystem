package com.swd.pregnancycare.implement;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.entity.BlogEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.InsertException;
import com.swd.pregnancycare.repository.BlogRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.BlogServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogServices {
  @Autowired
  private BlogRepo blogRepo;
  @Autowired
  private UserRepo userRepo;

  @Transactional
  @Override
  public BaseResponse saveBlog(BlogRequest blog) {
      Optional<UserEntity> user = userRepo.findByEmail(blog.getEmail());
      BaseResponse response = new BaseResponse();

      if (user.isPresent()) {
        UserEntity userEntity = user.get();
        BlogEntity newBlog = new BlogEntity();

        newBlog.setTitle(blog.getTitle());
        newBlog.setDescription(blog.getDescription());
        newBlog.setStatus(false);
        newBlog.setDatePublish(LocalDateTime.now());
        newBlog.setUser(userEntity);
        blogRepo.save(newBlog);

        response.setCode(200);
        response.setMessage("Saved a blog successfully");
      }
      else {
        response.setCode(400);
        response.setMessage("User not found");
      }
      return response;
  }
  @Transactional
  @Override
  public BaseResponse getAllBlogs() {
    BaseResponse response = new BaseResponse();
    if(blogRepo.findAll().isEmpty()) {
      response.setCode(400);
      response.setMessage("Is empty");
      return response;
    }
    else {
      response.setData(blogRepo.findAll().stream().map(data -> {
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setId(data.getId());
        blogDTO.setTitle(data.getTitle());
        blogDTO.setDescription(data.getDescription());
        blogDTO.setDatePublish(data.getDatePublish());
        blogDTO.setStatus(data.getStatus());
        if (data.getUser() != null) {
          blogDTO.setUserId(data.getUser().getId());
        }
        return blogDTO;
      }).toList());
      response.setCode(200);
      response.setMessage("Got all blogs successfully");
      return response;
    }
  }

  @Transactional
  @Override
  public BaseResponse deleteBlog(int blogId) {
      Optional<BlogEntity> blog = blogRepo.findById(blogId);
      BaseResponse baseResponse = new BaseResponse();
      if(blog.isPresent()) {
        blogRepo.deleteById(blog.get().getId());
        baseResponse.setCode(200);
        baseResponse.setMessage("Deleted blog successfully");
      } else {
        baseResponse.setCode(400);
        baseResponse.setMessage("Blog not found");
      }
      return baseResponse;
  }
}
