package com.project.shopapp.repositories;

import com.project.shopapp.models.Category;
import com.project.shopapp.models.OrderDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByActiveTrue();

    // Tìm kiếm category với active = false và theo từ khóa
    @Query("SELECT c FROM Category c WHERE c.active = false AND c.name LIKE %:keyword%")
    List<Category> searchByKeywordAndInactive(String keyword);

    // Cập nhật trạng thái active = true cho category với id cụ thể
    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.active = true WHERE c.id = :id")
    int activateCategoryById(Long id);
}
