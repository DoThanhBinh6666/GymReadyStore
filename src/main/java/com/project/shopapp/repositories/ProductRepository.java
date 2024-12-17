package com.project.shopapp.repositories;

import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Kiểm tra sản phẩm tồn tại với tên cụ thể
    boolean existsByName(String name);

    // Phân trang với điều kiện active = true
    Page<Product> findAll(Pageable pageable); // Giữ nguyên phương thức, nhưng bạn có thể cần điều chỉnh khi gọi từ service.

    // Tìm sản phẩm theo danh mục và active = true
    List<Product> findByCategory(Category category);

    // Tìm sản phẩm theo từ khóa và danh mục với điều kiện active = true
    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%) " +
            "AND p.active = true")
    Page<Product> searchProducts(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword, Pageable pageable);

    // Lấy chi tiết sản phẩm với điều kiện active = true
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id = :productId AND p.active = true")
    Optional<Product> getDetailProduct(@Param("productId") Long productId);

    // Tìm các sản phẩm theo danh sách ID với điều kiện active = true
    @Query("SELECT p FROM Product p WHERE p.id IN :productIds AND p.active = true")
    List<Product> findProductsByIds(@Param("productIds") List<Long> productIds);

    // Tìm các sản phẩm yêu thích của người dùng với điều kiện active = true
    @Query("SELECT p FROM Product p JOIN p.favorites f WHERE f.user.id = :userId AND p.active = true")
    List<Product> findFavoriteProductsByUserId(@Param("userId") Long userId);

    // Xóa mềm các sản phẩm theo categoryId và chỉ xóa các sản phẩm có active = true
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.active = false WHERE p.category.id = :categoryId AND p.active = true")
    void deleteByCategoryIdAndActiveTrue(Long categoryId);
    // Tìm tất cả sản phẩm có active = false
    @Query("SELECT p FROM Product p WHERE p.active = false")
    List<Product> findInactiveProducts();
    // Tìm sản phẩm theo từ khóa và điều kiện active = false
    @Query("SELECT p FROM Product p WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%) " +
            "AND p.active = false")
    Page<Product> searchInactiveProducts(
            @Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdIgnoreActive(@Param("productId") Long productId);


}
