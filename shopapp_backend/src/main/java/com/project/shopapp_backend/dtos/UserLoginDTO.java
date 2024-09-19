package com.project.shopapp_backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @JsonProperty("phone_number")
    @NotBlank(message = "so dien thoai khong duoc de trong")
    private String phoneNumber;

    @NotBlank(message = "khong duoc de trong password ")
    private String password;
}
