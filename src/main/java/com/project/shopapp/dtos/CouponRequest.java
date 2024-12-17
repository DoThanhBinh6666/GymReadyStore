package com.project.shopapp.dtos;

import com.project.shopapp.models.CouponCondition;
import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponRequest {
    private String code;
    private boolean active;
    private List<CouponCondition> couponConditions;
}