package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author keenan on 28/03/2018
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon findFirstById(Long id);
}
