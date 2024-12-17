package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data // Tạo toString(), equals(), hashCode(), getters và setters
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementDTO {

    @NotBlank(message = "Tiêu đề là bắt buộc")
    @Size(min = 3, max = 200, message = "Tiêu đề phải có độ dài từ 3 đến 200 ký tự")
    private String title;

    @NotBlank(message = "Nội dung là bắt buộc")
    @Size(min = 5, message = "Nội dung phải có ít nhất 5 ký tự")
    private String content;

    private String thumbnail;
    private List<String> announcement_images;
}
