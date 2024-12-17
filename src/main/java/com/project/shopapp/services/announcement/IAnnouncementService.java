package com.project.shopapp.services.announcement;

import com.project.shopapp.dtos.AnnouncementDTO;
import com.project.shopapp.dtos.AnnouncementImageDTO;
import com.project.shopapp.responses.announcement.AnnouncementResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.project.shopapp.models.Announcement;
import com.project.shopapp.models.AnnouncementImage;

import java.util.List;

public interface IAnnouncementService {

    // Tạo mới một thông báo
    Announcement createAnnouncement(AnnouncementDTO announcementDTO) throws Exception;

    // Lấy thông báo theo ID
    Announcement getAnnouncementById(long id) throws Exception;

    // Lấy tất cả thông báo với điều kiện tìm kiếm và phân trang
    Page<AnnouncementResponse> getAllAnnouncements(String keyword, PageRequest pageRequest);

    // Cập nhật thông báo
    Announcement updateAnnouncement(long id, AnnouncementDTO announcementDTO) throws Exception;

    // Xóa thông báo
    void deleteAnnouncement(long id) throws Exception;

    // Kiểm tra nếu tên thông báo đã tồn tại
    boolean existsByTitle(String title);

    // Lấy danh sách thông báo theo ID
    List<Announcement> findAnnouncementsByIds(List<Long> announcementIds);

    // Tạo hình ảnh cho thông báo
    AnnouncementImage createAnnouncementImage(
            Long announcementId,
            AnnouncementImageDTO announcementImageDTO) throws Exception;
}
