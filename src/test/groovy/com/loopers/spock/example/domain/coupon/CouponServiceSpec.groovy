package com.loopers.spock.example.domain.coupon

import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoSettings
import spock.lang.Specification

@MockitoSettings
class CouponServiceSpec extends Specification {

    @Spy
    private CouponService couponService

    def "주문 금액을 받아서, 쿠폰 종류별 할인을 적용하여 최종 할인 금액을 반환한다"() {
        when:
        println "$orderPrice/ $coupons / $calculators / $expected / $a"

        then:
        couponService.getSalePrice(100, [], []) == 100

        where:
        _______________________________________________
        orderPrice | coupons | calculators
        123        | []      | [123]
        123        | []      | [123]
        _______________________________________________
        expected | a
        23       | 23
        2        | 65
    }

}
