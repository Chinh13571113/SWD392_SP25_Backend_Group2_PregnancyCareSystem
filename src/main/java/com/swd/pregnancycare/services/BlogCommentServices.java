package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogCommentDTO;

import java.util.List;

public interface BlogCommentServices {
  void saveBlogComment( int blogId, String description);
  void deleteBlogComment(int id);
  void updateBlogComment(int id, String description);
}
