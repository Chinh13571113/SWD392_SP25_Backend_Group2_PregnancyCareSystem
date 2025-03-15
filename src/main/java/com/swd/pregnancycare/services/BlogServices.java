package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.request.BlogRequest;

import java.util.List;

public interface BlogServices {
  void saveBlog(BlogRequest blog);
  List<BlogDTO> getAllBlogs();
  void deleteBlog(int blogId);
  void updateBlog(BlogRequest blogRequest, int id);
}
