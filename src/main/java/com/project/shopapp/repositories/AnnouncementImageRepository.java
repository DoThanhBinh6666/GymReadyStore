package com.project.shopapp.repositories;

import com.project.shopapp.models.AnnouncementImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnnouncementImageRepository extends JpaRepository<AnnouncementImage, Long> {

    // Phương thức xóa tất cả ảnh liên quan đến một announcement
    @Modifying
    @Transactional
    @Query("DELETE FROM AnnouncementImage ai WHERE ai.announcement.id = :announcementId")
    void deleteByAnnouncementId(Long announcementId);

    // Các phương thức khác (nếu cần)
    List<AnnouncementImage> findByAnnouncementId(Long announcementId);
}
