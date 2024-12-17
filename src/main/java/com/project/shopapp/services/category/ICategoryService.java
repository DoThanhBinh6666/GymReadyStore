package com.project.shopapp.services.category;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO category);
    Category deleteCategory(long id) throws Exception;
    // Tìm kiếm category chưa kích hoạt
    List<Category> searchInactiveCategories(String keyword);
    // Cập nhật trạng thái active = true
    boolean activateCategory(long id);
}
