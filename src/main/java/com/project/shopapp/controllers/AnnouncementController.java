package com.project.shopapp.controllers;

import com.project.shopapp.dtos.AnnouncementDTO;
import com.project.shopapp.dtos.AnnouncementImageDTO;
import com.project.shopapp.models.Announcement;
import com.project.shopapp.models.AnnouncementImage;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.responses.announcement.AnnouncementResponse;
import com.project.shopapp.services.announcement.AnnouncementService;
import com.project.shopapp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);

    private final AnnouncementService announcementService;

    // Tạo thông báo mới
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> createAnnouncement(
            @Valid @RequestBody AnnouncementDTO announcementDTO,
            BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(String.join("; ", errorMessages))
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }

        Announcement newAnnouncement = announcementService.createAnnouncement(announcementDTO);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Create new announcement successfully")
                        .status(HttpStatus.CREATED)
                        .data(newAnnouncement)
                        .build());
    }

    // Xóa thông báo
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> deleteAnnouncement(@PathVariable Long id) {
        try {
            announcementService.deleteAnnouncement(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Announcement deleted successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseObject.builder()
                            .message("Announcement not found")
                            .status(HttpStatus.NOT_FOUND)
                            .build());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody AnnouncementDTO announcementDTO
    ) throws Exception {
        Announcement updatedAnnouncement = announcementService.updateAnnouncement(id, announcementDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Announcement updated successfully")
                .status(HttpStatus.OK)
                .data(updatedAnnouncement)
                .build());
    }


    // Lấy thông báo theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getAnnouncementById(@PathVariable Long id) throws Exception {
        Announcement announcement = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Get announcement details successfully")
                .status(HttpStatus.OK)
                .data(announcement)
                .build());
    }

    // Lấy danh sách thông báo với phân trang và tìm kiếm theo từ khóa
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAnnouncements(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<AnnouncementResponse> announcementsPage = announcementService.getAllAnnouncements(keyword, pageRequest);

        return ResponseEntity.ok(ResponseObject.builder()
                .message("Get announcements successfully")
                .status(HttpStatus.OK)
                .data(announcementsPage.getContent())
                .build());
    }

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> uploadImagesForAnnouncement(
            @PathVariable("id") Long announcementId,
            @ModelAttribute("files") List<MultipartFile> files
    ) throws Exception {
        // Kiểm tra sự tồn tại của thông báo
        Announcement existingAnnouncement = announcementService.getAnnouncementById(announcementId);

        // Nếu không có file nào được tải lên
        files = files == null ? new ArrayList<MultipartFile>() : files;
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message("No files uploaded")
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }

        // Kiểm tra số lượng ảnh
        if (files.size() > AnnouncementImage.MAXIMUM_IMAGES_PER_ANNOUNCEMENT) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message("The number of images must be <= " + AnnouncementImage.MAXIMUM_IMAGES_PER_ANNOUNCEMENT)
                            .build()
            );
        }

        List<AnnouncementImage> announcementImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.getSize() == 0) {
                continue;
            }

            // Kiểm tra kích thước file và định dạng
            if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body(ResponseObject.builder()
                                .message("The file is too large. Maximum size is 10MB.")
                                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .build());
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body(ResponseObject.builder()
                                .message("The uploaded file must be an image.")
                                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .build());
            }

            // Lưu file và lấy tên file
            String filename = FileUtils.storeFile(file);

            // Tạo DTO cho AnnouncementImage
            AnnouncementImageDTO announcementImageDTO = new AnnouncementImageDTO();
            announcementImageDTO.setImageUrl(filename);

            // Gọi service để tạo hình ảnh cho thông báo
            AnnouncementImage announcementImage = announcementService.createAnnouncementImage(announcementId, announcementImageDTO);
            announcementImages.add(announcementImage);
        }

        // Trả về danh sách các ảnh đã tải lên
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Images uploaded successfully")
                        .status(HttpStatus.CREATED)
                        .data(announcementImages)
                        .build()
        );
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                logger.info(imageName + " not found");
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
                //return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while retrieving image: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
