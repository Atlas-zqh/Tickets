package com.keenan.Tickets.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;

/**
 * @author keenan on 28/03/2018
 */
@Entity
@Table(name = "user_coupon")
public class UserCoupon {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Coupon coupon;

    private Boolean used;

    public UserCoupon(User user, Coupon coupon, Boolean used) {
        this.user = user;
        this.coupon = coupon;
        this.used = used;
    }

    public UserCoupon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
}
