package com.project.shopapp.controllers;

import com.project.shopapp.models.AnnouncementImage;
import com.project.shopapp.responses.ResponseObject;

import com.project.shopapp.services.announcement.AnnouncementService;
import com.project.shopapp.services.announcement.image.AnnouncementImageService;
import com.project.shopapp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/announcement_images")
//@Validated
@RequiredArgsConstructor
public class AnnouncementImageController {
    private final AnnouncementImageService announcementImageService;
    private final AnnouncementService announcementService;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> delete(
            @PathVariable Long id
    ) throws Exception {
        AnnouncementImage announcementImage = announcementImageService.deleteAnnouncementImage(id);
        if(announcementImage != null){
            // Xóa file ảnh liên quan từ hệ thống
            FileUtils.deleteFile(announcementImage.getImageUrl());
        }
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Delete announcement image successfully")
                        .data(announcementImage)
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
