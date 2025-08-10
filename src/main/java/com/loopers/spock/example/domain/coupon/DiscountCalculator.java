package com.loopers.spock.example.domain.coupon;

import java.math.BigDecimal;

public interface DiscountCalculator {

    long calculate(long orderPrice, CouponType couponType, BigDecimal discountAmount);

}
