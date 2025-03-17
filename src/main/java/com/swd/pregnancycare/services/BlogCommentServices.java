package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogCommentDTO;
import com.swd.pregnancycare.response.BlogCommentResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface BlogCommentServices {
  void saveBlogComment( int blogId, String description);
  void deleteBlogComment(int id);
  void updateBlogComment(int id, String description);
  List<BlogCommentResponse> getAllComments(int blogId);
  List<BlogCommentResponse> getMyComments();
}
