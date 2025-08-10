package com.loopers.spock.example.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    public long getSalePrice(Long orderPrice, List<Coupon> coupons, List<DiscountCalculator> calculators) {
        long discountAmount = 0;

        for (Coupon coupon : coupons) {
            CouponType type = coupon.getType();
            BigDecimal amount = coupon.getDiscountAmount();

            for (DiscountCalculator calculator : calculators) {
                discountAmount += calculator.calculate(orderPrice, type, amount);
            }
        }

        return orderPrice - discountAmount;
    }

}
