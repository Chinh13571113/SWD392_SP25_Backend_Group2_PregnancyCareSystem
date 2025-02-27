package com.swd.pregnancycare.implement;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.entity.BlogEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.InsertException;
import com.swd.pregnancycare.repository.BlogRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.services.BlogServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogServices {
  @Autowired
  private BlogRepo blogRepo;
  @Autowired
  private UserRepo userRepo;

  @Transactional
  @Override
  public void saveBlog(BlogDTO blog) {
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
    } catch (Exception e) {
      throw new InsertException("Save blog error: " + e.getMessage());
    }
  }
}
