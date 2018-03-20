package com.keenan.Tickets.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author keenan on 19/03/2018
 */
@Entity
@Table(name = "level_coupon")
public class LevelCoupon {
    @Id
    @GeneratedValue
    private Long id;

    private String couponName;

    private Integer fitLevel;

    private Double discount;

    public LevelCoupon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getFitLevel() {
        return fitLevel;
    }

    public void setFitLevel(Integer fitLevel) {
        this.fitLevel = fitLevel;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
