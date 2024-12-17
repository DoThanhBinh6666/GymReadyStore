package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data // toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementImageDTO {

    @JsonProperty("announcement_id")
    @Min(value = 1, message = "Announcement's ID must be > 0")
    private Long announcementId;

    @Size(min = 5, max = 300, message = "Image URL must be between 5 and 300 characters")
    @JsonProperty("image_url")
    private String imageUrl;
}
