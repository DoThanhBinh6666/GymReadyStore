package com.project.shopapp.models;

import com.project.shopapp.services.announcement.IAnnouncementRedisService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@AllArgsConstructor
public class AnnouncementListener {

    private final IAnnouncementRedisService announcementRedisService;
    private static final Logger logger = LoggerFactory.getLogger(AnnouncementListener.class);

    @PrePersist
    public void prePersist(Announcement announcement) {
        logger.info("prePersist - Announcement with ID: {}", announcement.getId());
    }

    @PostPersist
    public void postPersist(Announcement announcement) {
        // Xử lý sau khi thông báo được lưu vào cơ sở dữ liệu
        logger.info("postPersist - Announcement with ID: {}", announcement.getId());
        // Cập nhật Redis cache hoặc thực hiện các hành động khác
        announcementRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(Announcement announcement) {
        logger.info("preUpdate - Announcement with ID: {}", announcement.getId());
    }

    @PostUpdate
    public void postUpdate(Announcement announcement) {
        // Cập nhật Redis cache hoặc thực hiện các hành động khác sau khi thông báo được cập nhật
        logger.info("postUpdate - Announcement with ID: {}", announcement.getId());
        announcementRedisService.clear();
    }

    @PreRemove
    public void preRemove(Announcement announcement) {
        logger.info("preRemove - Announcement with ID: {}", announcement.getId());
    }

    @PostRemove
    public void postRemove(Announcement announcement) {
        // Cập nhật Redis cache hoặc thực hiện các hành động khác sau khi thông báo bị xóa
        logger.info("postRemove - Announcement with ID: {}", announcement.getId());
        announcementRedisService.clear();
    }
}
