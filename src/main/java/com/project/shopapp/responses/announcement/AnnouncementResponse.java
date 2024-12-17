package com.project.shopapp.responses.announcement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Announcement;
import com.project.shopapp.responses.BaseResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementResponse extends BaseResponse {
    private Long id;
    private String title;
    private String content;
    private String thumbnail;

    @JsonProperty("announcement_images")
    private List<String> announcementImages = new ArrayList<>();

    // Phương thức chuyển đổi từ Announcement sang AnnouncementResponse
    public static AnnouncementResponse fromAnnouncement(Announcement announcement) {
        // Format ngày tháng nếu cần
        AnnouncementResponse announcementResponse = AnnouncementResponse.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .thumbnail(announcement.getThumbnail())
                .announcementImages(
                        announcement.getAnnouncementImages().stream()
                                .map(image -> image.getImageUrl()) // Giả sử `AnnouncementImage` có trường `imageUrl`
                                .toList()
                )
                .build();
        announcementResponse.setCreatedAt(announcement.getCreatedAt());
        announcementResponse.setUpdatedAt(announcement.getUpdatedAt());
        return announcementResponse;
    }
}
