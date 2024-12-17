package com.project.shopapp.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.shopapp.models.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
    //SELECT * FROM users WHERE phoneNumber=?
    //query command
    @Query("SELECT o FROM User o WHERE o.active = true AND (:keyword IS NULL OR :keyword = '' OR " +
            "o.fullName LIKE %:keyword% " +
            "OR o.address LIKE %:keyword% " +
            "OR o.phoneNumber LIKE %:keyword%) " +
            "AND LOWER(o.role.name) = 'user'")
    Page<User> findAll(@Param("keyword") String keyword, Pageable pageable);
    List<User> findByRoleId(Long roleId);

    Optional<User> findByFacebookAccountId(String facebookAccountId);
    Optional<User> findByGoogleAccountId(String googleAccountId);
    // Tìm user với điều kiện is_active = false và tìm kiếm theo từ khóa
    @Query("SELECT u FROM User u WHERE u.active = false AND (:keyword IS NULL OR :keyword = '' OR " +
            "u.fullName LIKE %:keyword% OR u.address LIKE %:keyword% OR u.phoneNumber LIKE %:keyword%)")
    Page<User> findInactiveUsers(@Param("keyword") String keyword, Pageable pageable);

    // Cập nhật is_active về true dựa trên ID
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.active = true WHERE u.id = :id")
    int activateUser(@Param("id") Long id);
}

