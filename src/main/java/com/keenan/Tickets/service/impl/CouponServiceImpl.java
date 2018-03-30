package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.model.Coupon;
import com.keenan.Tickets.model.User;
import com.keenan.Tickets.model.UserCoupon;
import com.keenan.Tickets.repository.CouponRepository;
import com.keenan.Tickets.repository.UserCouponRepository;
import com.keenan.Tickets.repository.UserRepository;
import com.keenan.Tickets.service.CouponService;
import com.keenan.Tickets.service.UserService;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keenan on 29/03/2018
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;

    @Override
    public List<Coupon> getAllCouponsNotExpired() {
        List<Coupon> coupons = couponRepository.findAll();
        Timestamp today = new Timestamp(System.currentTimeMillis());

        List<Coupon> validCoupon = new ArrayList<>();
        coupons.forEach(coupon -> {
            if (coupon.getExpireTime().after(today)) {
                validCoupon.add(coupon);
            }
        });
        return validCoupon;
    }

    @Override
    public ResultMessage changeCoupon(Long userId, Long couponId) {
        User user = userRepository.findFirstById(userId);
        Coupon coupon = couponRepository.findFirstById(couponId);

        if (user.getPoints() < coupon.getNeedPoints()) {
            return new ResultMessage(ResultMessage.ERROR, "剩余积分不足，不能兑换");
        }

        user.setPoints(user.getPoints() - coupon.getNeedPoints());
        userRepository.save(user);

        UserCoupon userCoupon = new UserCoupon(user, coupon, false);
        userCouponRepository.save(userCoupon);
        return new ResultMessage(ResultMessage.SUCCESS, "兑换成功");
    }
}
