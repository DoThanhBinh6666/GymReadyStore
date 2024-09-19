package com.project.shopapp_backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO
{
    @JsonProperty("order_id")
    @Min(value=1,message = "order_id phai > 0!")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1,message = "product_id phai lon hon 0!")
    private int productId;

    @Min(value = 0,message = "price toi thieu phai lon hon hoac bang 0!")
    private Long price;

    @Min(value = 1,message = "so san pham phai lon hon 1")
    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value = 0,message = "tong tien phai >= 0")
    private int totalMoney;

    private String color;
}
