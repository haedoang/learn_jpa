package io.haedoang.transactional.user.domain.application;

import io.haedoang.transactional.api.application.dto.SignUpRequest;
import io.haedoang.transactional.coupon.domain.Coupon;
import io.haedoang.transactional.coupon.domain.applicatioion.CouponService;
import io.haedoang.transactional.user.domain.User;
import io.haedoang.transactional.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.*;

/**
 * author : haedoang
 * date : 2022-08-23
 * description :
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final CouponService couponService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(SignUpRequest request) {
        User savedUser = userRepository.save(new User(request.getUsername(), request.getPassword()));
        Coupon savedCoupon = couponService.save(savedUser.getId());
        log.info("savedCoupon: {}", savedCoupon);
        return savedUser;
    }

    @Transactional
    public User saveFailByChild(SignUpRequest request) {
        User savedUser = userRepository.save(new User(request.getUsername(), request.getPassword()));
        couponService.saveThrowError(savedUser.getId());
        return savedUser;
    }

    @Transactional
    public User savedFailByChildWithRequiredsNewPropagation(SignUpRequest request) {
        User savedUser = userRepository.save(new User(request.getUsername(), request.getPassword()));
        try {
            couponService.saveRequiredsNew(savedUser.getId());
        } catch (RuntimeException e) {
            log.error("@throw error: {}", e.getMessage());
        }
        return savedUser;
    }

    @Transactional
    public User savedFailByChildWithNestedPropagation(SignUpRequest request) {
        User savedUser = userRepository.save(new User(request.getUsername(), request.getPassword()));
        try {
            couponService.savedNested(savedUser.getId());
        } catch (RuntimeException e) {
            log.error("@throw error: {}", e.getMessage());
        }
        return savedUser;
    }

    //FIXME 트랜잭션 확인하기
    @Transactional
    public User savedFailByParentWithRequiredsNewPropagation(SignUpRequest request) {
        try {
            userRepository.save(new User(null, null));
        } catch (RuntimeException e) {
            log.error("@throw error: {}",  e.getMessage());
        }

        couponService.saveRequiredsNew(1L);
        return null;
    }

//    @Transactional
//    public void saveFail_required(SignUpRequest request) {
//        userRepository.save(new User(request.getUsername(), request.getPassword()));
//        couponService.save(null);
//    }
//
//    @Transactional
//    public void saveFail_requireds_new(SignUpRequest request) {
//        userRepository.save(new User(request.getUsername(), request.getPassword()));
//        /** 별개의 트랜잭션 */
//        try {
//            couponService.saveRequiredsNew(null);
//        } catch (Exception e) {
//            log.error("@@throw error : {}", e.getMessage());
//        }
//    }
//
//    @Transactional
//    public void saveFail_requireds_new_ThrowParent(SignUpRequest request) {
//        User savedUser = userRepository.save(new User(request.getUsername(), request.getPassword()));
//        couponService.saveRequiredsNew(savedUser.getId());
//        throw new RuntimeException();
//    }
}
