package com.project.shopapp.repositories;

import com.project.shopapp.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    // Kiểm tra sự tồn tại của Announcement với tiêu đề cụ thể
    boolean existsByTitle(String title);

    // Lấy tất cả Announcement với phân trang
    Page<Announcement> findAll(Pageable pageable);

    // Tìm kiếm Announcement với keyword và phân trang
    @Query("SELECT a FROM Announcement a WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR a.title LIKE %:keyword% OR a.content LIKE %:keyword%)")
    Page<Announcement> searchAnnouncements(@Param("keyword") String keyword, Pageable pageable);

    // Lấy thông báo chi tiết theo id
    @Query("SELECT a FROM Announcement a WHERE a.id = :announcementId")
    Optional<Announcement> getAnnouncementById(@Param("announcementId") Long announcementId);

    // Tìm các Announcement theo danh sách ID
    @Query("SELECT a FROM Announcement a WHERE a.id IN :announcementIds")
    List<Announcement> findByIdIn(@Param("announcementIds") List<Long> announcementIds);
}
