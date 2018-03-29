package com.keenan.Tickets.service;

import com.keenan.Tickets.model.Coupon;
import com.keenan.Tickets.util.ResultMessage;

import java.util.List;

/**
 * @author keenan on 29/03/2018
 */
public interface CouponService {
    List<Coupon> getAllCouponsNotExpired();

    ResultMessage changeCoupon(Long userId, Long couponId);
}
