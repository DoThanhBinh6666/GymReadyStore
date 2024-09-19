package com.project.shopapp_backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "title is required")
    @Size(min=3,max=200,message = "phai it nhat tu 3 den 200 ki tu")
    private String name;

    @Min(value=0,message = "gia phai lon hon 0")
    @Max(value = 10000000,message = "gia khong duoc qua 10000000")
    private Float price;

    private String description;

    private String thumbnail;

    @JsonProperty("category_id")
    private String categoryId;

    private List<MultipartFile> files;
}
