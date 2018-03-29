package com.keenan.Tickets.bean;

/**
 * @author keenan on 28/03/2018
 */
public class CouponBriefInfoBean {
    public Long userCouponId;

    public String couponName;

    public Double discount;

    public String expireDate;

    public Double requirePoints;

    public CouponBriefInfoBean(Long userCouponId, String couponName, Double discount) {
        this.userCouponId = userCouponId;
        this.couponName = couponName;
        this.discount = discount;
    }

    public CouponBriefInfoBean(Long userCouponId, String couponName, Double discount, String expireDate, Double requirePoints) {
        this.userCouponId = userCouponId;
        this.couponName = couponName;
        this.discount = discount;
        this.expireDate = expireDate;
        this.requirePoints = requirePoints;
    }

    public CouponBriefInfoBean() {
    }
}
