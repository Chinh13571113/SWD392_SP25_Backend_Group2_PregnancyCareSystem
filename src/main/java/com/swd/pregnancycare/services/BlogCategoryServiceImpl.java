package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.AdviceDTO;
import com.swd.pregnancycare.dto.BlogCategoryDTO;
import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.entity.BlogCategoryEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.BlogCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogCategoryServiceImpl implements BlogCategoryServices {
  @Autowired
  private BlogCategoryRepo blogCategoryRepo;

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void saveBlogCategory(String name, String description) {
    Optional<BlogCategoryEntity> blogCategory = blogCategoryRepo.findByNameAndDeletedFalse(name);

    if(blogCategory.isEmpty()) {
      BlogCategoryEntity blogCategoryEntity = new BlogCategoryEntity();
      blogCategoryEntity.setName(name);
      blogCategoryEntity.setSlug(generateSlug(name));
      blogCategoryEntity.setDescription(description);
      blogCategoryEntity.setDatePublish(LocalDateTime.now());
      blogCategoryEntity.setDeleted(false);
      blogCategoryRepo.save(blogCategoryEntity);
    } else throw new AppException(ErrorCode.BLOG_CATEGORY_EXIST);
  }

  @Override
  @PreAuthorize("hasAnyRole('MEMBER', 'EXPERT', 'ADMIN')")
  public List<BlogCategoryDTO> getAllBlogCategories() {
    return blogCategoryRepo.findAll().stream().map(data -> {
      BlogCategoryDTO blogCategory = new BlogCategoryDTO();
      blogCategory.setId(data.getId());
      blogCategory.setName(data.getName());
      blogCategory.setSlug(data.getSlug());
      blogCategory.setDescription(data.getDescription());
      blogCategory.setDatePublish(data.getDatePublish());
      blogCategory.setDeleted(data.getDeleted());

      if (data.getBlogs() != null) {
        List<BlogDTO> blogs = data.getBlogs().stream().map(blogEntity -> {
          BlogDTO blog = new BlogDTO();
          blog.setId(blogEntity.getId());
          blog.setTitle(blogEntity.getTitle());
          blog.setDescription(blogEntity.getDescription());
          blog.setDatePublish(blogEntity.getDatePublish());
          blog.setStatus(blogEntity.getStatus());
          blog.setDeleted(blogEntity.getDeleted());
          return blog;
        }).collect(Collectors.toList());
        blogCategory.setBlogs(blogs);
      }

      return blogCategory;
    }).toList();
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteBlogCategory(int id) {
    BlogCategoryEntity blogCategory = blogCategoryRepo.findById(id).orElseThrow(()-> new AppException(ErrorCode.BLOG_CATEGORY_NOT_EXIST));
    try {
      blogCategory.setDeleted(true);
      blogCategoryRepo.save(blogCategory);
    } catch (AppException app) {
      throw new AppException(ErrorCode.BLOG_CATEGORY_DELETE_FAILED);
    }
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void updateBlogCategory(int id, String name, String description) {
    BlogCategoryEntity blogCategory = blogCategoryRepo.findById(id).orElseThrow(()-> new AppException(ErrorCode.BLOG_CATEGORY_NOT_EXIST));
    try {
      blogCategory.setName(name);
      blogCategory.setDescription(description);
      blogCategory.setSlug(generateSlug(name));
      blogCategoryRepo.save(blogCategory);
    } catch (Exception e) {
      throw new AppException(ErrorCode.BLOG_CATEGORY_UPDATE_FAILED);
    }
  }


  public String generateSlug(String name) {
    // Convert the name to lowercase, trim it, and replace spaces with hyphens
    return name.trim().toLowerCase().replaceAll("\\s+", "-");
  }
}
