package com.keenan.Tickets.bean;

/**
 * @author keenan on 28/03/2018
 */
public class CouponBriefInfoBean {
    public Long userCouponId;

    public String couponName;

    public Double discount;

    public CouponBriefInfoBean(Long userCouponId, String couponName, Double discount) {
        this.userCouponId = userCouponId;
        this.couponName = couponName;
        this.discount = discount;
    }

    public CouponBriefInfoBean() {
    }
}
