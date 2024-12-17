package com.project.shopapp.services.announcement.image;

import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.AnnouncementImage;
import com.project.shopapp.repositories.AnnouncementImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementImageService implements IAnnouncementImageService {

    private final AnnouncementImageRepository announcementImageRepository;

    @Override
    @Transactional
    public AnnouncementImage deleteAnnouncementImage(Long id) throws Exception {
        Optional<AnnouncementImage> announcementImage = announcementImageRepository.findById(id);

        if (announcementImage.isEmpty()) {
            throw new DataNotFoundException(
                    String.format("Cannot find announcement image with id: %d", id)
            );
        }

        // Xóa ảnh thông báo
        announcementImageRepository.deleteById(id);

        // Trả về ảnh đã xóa (hoặc có thể không cần trả về tùy vào yêu cầu)
        return announcementImage.get();
    }
}
