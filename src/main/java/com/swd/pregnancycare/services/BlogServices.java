package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.response.BaseResponse;

import java.util.List;

public interface BlogServices {
  void saveBlog(BlogRequest blog);
  List<BlogDTO> getAllBlogs();
  BaseResponse deleteBlog(int blogId);
}
