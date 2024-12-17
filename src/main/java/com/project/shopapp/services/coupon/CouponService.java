package com.project.shopapp.services.coupon;

import com.project.shopapp.models.Coupon;
import com.project.shopapp.models.CouponCondition;
import com.project.shopapp.repositories.CouponConditionRepository;
import com.project.shopapp.repositories.CouponRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.project.shopapp.dtos.CouponRequest;

import java.util.List;
import java.time.LocalDate;


@RequiredArgsConstructor
@Service
public class CouponService implements ICouponService{
    private final CouponRepository couponRepository;
    private final CouponConditionRepository couponConditionRepository;
    @Override
    public double calculateCouponValue(String couponCode, double totalAmount) {
        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found"));
        if (!coupon.isActive()) {
            throw new IllegalArgumentException("Coupon is not active");
        }
        double discount = calculateDiscount(coupon, totalAmount);
        double finalAmount = totalAmount - discount;
        return finalAmount;
    }

    private double calculateDiscount(Coupon coupon, double totalAmount) {
        List<CouponCondition> conditions = couponConditionRepository
                .findByCouponId(coupon.getId());
        double discount = 0.0;
        double updatedTotalAmount = totalAmount;

        for (CouponCondition condition : conditions) {
            // Lấy thông tin điều kiện
            String attribute = condition.getAttribute();
            String operator = condition.getOperator();
            String value = condition.getValue();

            double percentDiscount = Double.valueOf(String.valueOf(condition.getDiscountAmount()));

            if (attribute.equals("minimum_amount")) {
                if (operator.equals(">") && updatedTotalAmount > Double.parseDouble(value)) {
                    discount += updatedTotalAmount * percentDiscount / 100;
                }
            } else if (attribute.equals("applicable_date")) {
                // Nếu điều kiện là BETWEEN, lấy ngày bắt đầu và kết thúc
                if (operator.equalsIgnoreCase("BETWEEN")) {
                    // Parse startDate và endDate từ value (giả sử value là một chuỗi JSON)
                    String[] dateRange = value.split(",");
                    LocalDate startDate = LocalDate.parse(dateRange[0].trim());
                    LocalDate endDate = LocalDate.parse(dateRange[1].trim());

                    LocalDate currentDate = LocalDate.now();
                    if (currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
                        discount += updatedTotalAmount * percentDiscount / 100;
                    }
                }
            }

            updatedTotalAmount = updatedTotalAmount - discount;
        }
        return discount;
    }


    @Transactional
    public Coupon createCouponWithConditions(CouponRequest couponRequest) {
        Coupon coupon = Coupon.builder()
                .code(couponRequest.getCode())
                .active(couponRequest.isActive())
                .build();
        Coupon savedCoupon = couponRepository.save(coupon);

        List<CouponCondition> conditions = couponRequest.getCouponConditions();
        for (CouponCondition condition : conditions) {
            condition.setCoupon(savedCoupon); // Set the coupon to the condition
            couponConditionRepository.save(condition);
        }
        return savedCoupon;
    }
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Transactional
    public void deleteCoupon(Long couponId) {
        // First, delete related CouponConditions
        couponConditionRepository.deleteByCouponId(couponId);

        // Then, delete the Coupon
        couponRepository.deleteById(couponId);
    }

}
