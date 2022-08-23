package io.haedoang.transactional.coupon.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author : haedoang
 * date : 2022-08-23
 * description :
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
