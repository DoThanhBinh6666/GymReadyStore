package com.project.shopapp.services.category;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category
                .builder()
                .name(categoryDTO.getName())
                .active(true)
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findByActiveTrue();
    }

    @Override
    @Transactional
    public Category updateCategory(long categoryId,
                                   CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Override
    @Transactional
    public Category deleteCategory(long id) throws Exception {
        // Lấy danh mục cần xóa
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Kiểm tra xem danh mục có sản phẩm liên kết không
        List<Product> products = productRepository.findByCategory(category);

        if (!products.isEmpty()) {
            for (Product product : products) {
                product.setActive(false);
                productRepository.save(product);
            }
        }

        // Chỉ thay đổi trạng thái active của danh mục thành false (xóa mềm danh mục)
        category.setActive(false);
        categoryRepository.save(category);

        return category;
    }
    @Override
    public List<Category> searchInactiveCategories(String keyword) {
        return categoryRepository.searchByKeywordAndInactive(keyword);
    }

    @Override
    public boolean activateCategory(long id) {
        int rowsAffected = categoryRepository.activateCategoryById(id);
        return rowsAffected > 0;
    }
}
