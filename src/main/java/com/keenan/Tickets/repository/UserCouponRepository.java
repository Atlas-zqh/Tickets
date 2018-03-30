package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author keenan on 29/03/2018
 */
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    UserCoupon findFirstById(Long id);
}
