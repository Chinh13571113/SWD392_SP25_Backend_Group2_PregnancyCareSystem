package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogCategoryDTO;

import java.util.List;

public interface BlogCategoryServices {
  void saveBlogCategory(String name, String description);
  List<BlogCategoryDTO> getAllBlogCategories();
  void deleteBlogCategory(int id);
  void updateBlogCategory(int id, String name, String description);
}
