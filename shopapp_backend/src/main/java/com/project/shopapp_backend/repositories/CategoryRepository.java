package com.project.shopapp_backend.repositories;

import com.project.shopapp_backend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
