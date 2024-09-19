package com.project.shopapp_backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @Min(value=1,message = "user_id toi thieu bang 1")
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("fullname")
    private String fullName;

    private String email;

    @NotBlank(message = "khong duoc bo trong so dien thoai")
    @JsonProperty("phone_number")
    @Size(min=5,message = "so dien thoai toi thieu 5 ky tu")
    private String phoneNumber;

    private String address;

    private String note;

    @Min(value = 0,message = "tong tien khong the duoi 0")
    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;
}
