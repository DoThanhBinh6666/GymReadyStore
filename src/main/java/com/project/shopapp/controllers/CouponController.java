package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CouponRequest;
import com.project.shopapp.models.Coupon;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.responses.coupon.CouponCalculationResponse;
import com.project.shopapp.services.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/coupons")
//@Validated
//Dependency Injection
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    @GetMapping("/calculate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> calculateCouponValue(
            @RequestParam("couponCode") String couponCode,
            @RequestParam("totalAmount") double totalAmount) {
        double finalAmount = couponService.calculateCouponValue(couponCode, totalAmount);
        CouponCalculationResponse couponCalculationResponse = CouponCalculationResponse.builder()
                .result(finalAmount)
                .build();
        return ResponseEntity.ok(new ResponseObject(
                "Calculate coupon successfully",
                HttpStatus.OK,
                couponCalculationResponse
        ));
    }
    // API tạo mới coupon
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> createCoupon(@RequestBody CouponRequest couponRequest) {
        try {
            Coupon createdCoupon = couponService.createCouponWithConditions(couponRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(
                    "Coupon created successfully",
                    HttpStatus.CREATED,
                    createdCoupon
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject(
                    "Failed to create coupon: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            ));
        }
    }
    // API to get all coupons
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(new ResponseObject(
                "Retrieved all coupons successfully",
                HttpStatus.OK,
                coupons
        ));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> deleteCoupon(@PathVariable("id") Long couponId) {
        try {
            couponService.deleteCoupon(couponId);
            return ResponseEntity.ok(new ResponseObject(
                    "Coupon deleted successfully",
                    HttpStatus.OK,
                    null
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject(
                    "Failed to delete coupon: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            ));
        }
    }

}
