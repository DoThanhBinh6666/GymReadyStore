package com.project.shopapp.services.announcement.image;

import com.project.shopapp.models.Announcement;
import com.project.shopapp.models.AnnouncementImage;
import com.project.shopapp.models.ProductImage;

public interface IAnnouncementImageService {
    AnnouncementImage deleteAnnouncementImage(Long id) throws Exception;
}
