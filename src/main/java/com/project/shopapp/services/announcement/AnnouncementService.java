package com.project.shopapp.services.announcement;

import com.project.shopapp.dtos.AnnouncementDTO;
import com.project.shopapp.dtos.AnnouncementImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.models.Announcement;
import com.project.shopapp.models.AnnouncementImage;
import com.project.shopapp.repositories.AnnouncementRepository;
import com.project.shopapp.repositories.AnnouncementImageRepository;
import com.project.shopapp.responses.announcement.AnnouncementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService implements IAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementImageRepository announcementImageRepository;

    @Override
    @Transactional
    public Announcement createAnnouncement(AnnouncementDTO announcementDTO) throws Exception {
        Announcement newAnnouncement = Announcement.builder()
                .title(announcementDTO.getTitle())
                .content(announcementDTO.getContent())
                .thumbnail(announcementDTO.getThumbnail()) // Add thumbnail if provided
                .build();
        return announcementRepository.save(newAnnouncement);
    }

    @Override
    public Announcement getAnnouncementById(long id) throws Exception {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find announcement with id =" + id));
    }


    @Override
    public Page<AnnouncementResponse> getAllAnnouncements(String keyword, PageRequest pageRequest) {
        Page<Announcement> announcementsPage = announcementRepository.searchAnnouncements(keyword, pageRequest);
        return announcementsPage.map(AnnouncementResponse::fromAnnouncement);
    }

    @Override
    @Transactional
    public Announcement updateAnnouncement(long id, AnnouncementDTO announcementDTO) throws Exception {
        // Lấy thông báo hiện tại từ cơ sở dữ liệu
        Announcement existingAnnouncement = getAnnouncementById(id);

        // Cập nhật tiêu đề
        if (announcementDTO.getTitle() != null && !announcementDTO.getTitle().isEmpty()) {
            existingAnnouncement.setTitle(announcementDTO.getTitle());
        }

        // Cập nhật nội dung
        if (announcementDTO.getContent() != null && !announcementDTO.getContent().isEmpty()) {
            existingAnnouncement.setContent(announcementDTO.getContent());
        }

        // Xử lý cập nhật ảnh
        if (announcementDTO.getAnnouncement_images() != null && !announcementDTO.getAnnouncement_images().isEmpty()) {
            // Xóa ảnh cũ
            announcementImageRepository.deleteByAnnouncementId(id);

            // Cập nhật danh sách ảnh mới
            for (String imageUrl : announcementDTO.getAnnouncement_images()) {
                AnnouncementImage newAnnouncementImage = AnnouncementImage.builder()
                        .announcement(existingAnnouncement)
                        .imageUrl(imageUrl)
                        .build();
                announcementImageRepository.save(newAnnouncementImage);
            }

            // Cập nhật `thumbnail` nếu ảnh mới có tồn tại
            if (announcementDTO.getThumbnail() != null && !announcementDTO.getThumbnail().isEmpty()) {
                existingAnnouncement.setThumbnail(announcementDTO.getThumbnail());
            } else {
                // Gán ảnh đầu tiên làm `thumbnail` nếu chưa có
                existingAnnouncement.setThumbnail(announcementDTO.getAnnouncement_images().get(0));
            }
        }

        // Lưu thông báo đã cập nhật
        return announcementRepository.save(existingAnnouncement);
    }



    @Override
    @Transactional
    public void deleteAnnouncement(long id) throws Exception {
        Announcement existingAnnouncement = getAnnouncementById(id);
        if (existingAnnouncement != null) {
            announcementRepository.delete(existingAnnouncement);
        }
    }

    @Override
    public boolean existsByTitle(String title) {
        return announcementRepository.existsByTitle(title);
    }

    @Override
    public List<Announcement> findAnnouncementsByIds(List<Long> announcementIds) {
        return announcementRepository.findByIdIn(announcementIds);
    }

    @Override
    @Transactional
    public AnnouncementImage createAnnouncementImage(Long announcementId, AnnouncementImageDTO announcementImageDTO) throws Exception {
        // Tìm thông báo theo ID
        Announcement existingAnnouncement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find announcement with id: " + announcementId));

        // Tạo đối tượng AnnouncementImage mới
        AnnouncementImage newAnnouncementImage = AnnouncementImage.builder()
                .announcement(existingAnnouncement)
                .imageUrl(announcementImageDTO.getImageUrl())
                .build();

        // Kiểm tra số lượng ảnh của thông báo
        int size = announcementImageRepository.findByAnnouncementId(announcementId).size();
        if (size >= AnnouncementImage.MAXIMUM_IMAGES_PER_ANNOUNCEMENT) {
            throw new InvalidParamException("Number of images must be <= " + AnnouncementImage.MAXIMUM_IMAGES_PER_ANNOUNCEMENT);
        }

        // Nếu thông báo chưa có thumbnail, gán ảnh đầu tiên làm thumbnail
        if (existingAnnouncement.getThumbnail() == null) {
            existingAnnouncement.setThumbnail(newAnnouncementImage.getImageUrl());
        }

        // Lưu thông báo với thumbnail mới (nếu có) và ảnh thông báo
        announcementRepository.save(existingAnnouncement);

        // Lưu hình ảnh mới cho thông báo
        return announcementImageRepository.save(newAnnouncementImage);
    }

}
