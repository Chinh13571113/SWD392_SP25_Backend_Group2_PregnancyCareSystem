package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.entity.BlogEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.InsertException;
import com.swd.pregnancycare.repository.BlogRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.response.BaseResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImp implements BlogServices {
  @Autowired
  private BlogRepo blogRepo;
  @Autowired
  private UserRepo userRepo;

  @Transactional
  @Override
  public void saveBlog(BlogRequest blog) {
    try {
      Optional<UserEntity> user = userRepo.findByEmail(blog.getEmail());
      if (user.isPresent()) {
        UserEntity userEntity = user.get();
        BlogEntity newBlog = new BlogEntity();

        newBlog.setTitle(blog.getTitle());
        newBlog.setDescription(blog.getDescription());
        newBlog.setStatus(false);
        newBlog.setDatePublish(LocalDateTime.now());
        newBlog.setUser(userEntity);
        blogRepo.save(newBlog);
      }
      else throw new InsertException("Save blog error: ");
    } catch (Exception e) {
      throw new InsertException("Save blog error: " + e.getMessage());
    }
  }
  @Transactional
  @Override
  public List<BlogDTO> getAllBlogs() {
    return blogRepo.findAll().stream().map(data -> {
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
    }).toList();
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
        baseResponse.setCode(404);
        baseResponse.setMessage("Blog not found");
      }
      return baseResponse;
  }
}
