package com.keenan.Tickets.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * @author keenan on 19/03/2018
 */
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue
    private Long id;

    private String couponName;

    private Double needPoints;

    private Timestamp expireTime;

    private Double discount;

    @OneToMany(mappedBy = "coupon", fetch = FetchType.EAGER)
    private Set<UserCoupon> users;

    public Coupon() {
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

    public Double getNeedPoints() {
        return needPoints;
    }

    public void setNeedPoints(Double needPoints) {
        this.needPoints = needPoints;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Set<UserCoupon> getUsers() {
        return users;
    }

    public void setUsers(Set<UserCoupon> users) {
        this.users = users;
    }
}
