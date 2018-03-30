package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.LevelCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author keenan on 28/03/2018
 */
public interface LevelCouponRepository extends JpaRepository<LevelCoupon, Long> {
    LevelCoupon findFirstById(Long id);
}
