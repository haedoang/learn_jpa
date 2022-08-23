package io.haedoang.transactional.coupon.domain.applicatioion;

import io.haedoang.transactional.coupon.domain.Coupon;
import io.haedoang.transactional.coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author : haedoang
 * date : 2022-08-23
 * description :
 */
@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    @Transactional
    public Coupon save(Long userId) {
        return couponRepository.save(new Coupon(userId));
    }

    @Transactional
    public void saveThrowError(Long userId) {
        couponRepository.save(new Coupon(userId));
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Coupon saveRequiredsNew(Long userId) {
        return couponRepository.save(new Coupon(userId));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Coupon saveMandatory(Long userId) {
        return couponRepository.save(new Coupon(userId));
    }

    @Transactional(propagation = Propagation.NEVER)
    public Coupon saveNeverThrowError(Long userId) {
        couponRepository.save(new Coupon(userId));
        couponRepository.save(new Coupon(userId));
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.NESTED)
    public Coupon savedNested(Long userId) {
        return couponRepository.save(new Coupon(userId));
    }
}
