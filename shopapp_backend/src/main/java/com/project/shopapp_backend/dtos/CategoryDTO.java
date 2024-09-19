package com.project.shopapp_backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // hàm khởi tạo mặc định không tham số
public class CategoryDTO {
    @NotEmpty(message = "ko dc de trong")
    private String name;
}
