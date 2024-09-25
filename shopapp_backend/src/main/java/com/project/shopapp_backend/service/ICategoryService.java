package com.project.shopapp_backend.service;

import com.project.shopapp_backend.dtos.CategoryDTO;
import com.project.shopapp_backend.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id );
    List<Category> getAllCategories();
    Category updateCategory(long categoryId,CategoryDTO category);
    void deleteCategory(long id);
}
