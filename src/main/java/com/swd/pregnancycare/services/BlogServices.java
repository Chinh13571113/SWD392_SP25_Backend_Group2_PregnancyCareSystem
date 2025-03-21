package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.response.BlogResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface BlogServices {
  void saveBlog(BlogRequest blog);


  public void approveBlog(int blogId);
  List<BlogDTO> getAllBlogsByMember();
  List<BlogDTO> getAllBlogsByExpert();
  void deleteBlog(int blogId);
  void updateBlog(BlogRequest blogRequest, int id);
  List<BlogDTO> getMyBlogs();
  BlogResponse getPostDetail(int blogId);
}
