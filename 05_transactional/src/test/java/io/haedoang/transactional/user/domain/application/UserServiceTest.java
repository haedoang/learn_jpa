package io.haedoang.transactional.user.domain.application;

import io.haedoang.transactional.api.application.dto.SignUpRequest;
import io.haedoang.transactional.coupon.domain.applicatioion.CouponService;
import io.haedoang.transactional.utils.DBCleanUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.NestedTransactionNotSupportedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * author : haedoang
 * date : 2022-08-23
 * description :
 */
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private DBCleanUp dbCleanUp;

    @BeforeEach
    void setUp() {
        dbCleanUp.afterPropertiesSet();
        dbCleanUp.execute();
    }

    @Test
    @DisplayName("사용자 등록 시 쿠폰이 발급된다")
    public void saveUserAndCoupon() {
        // given
        assertThat(userService.findAll()).hasSize(0);
        assertThat(couponService.findAll()).hasSize(0);

        // when
        userService.save(new SignUpRequest("haedoang", " 1234"));

        // then
        assertThat(userService.findAll()).hasSize(1);
        assertThat(couponService.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("[REQUIRED] Required Propagation은 부모/자식 내 예외가 발생할 경우 전체 트랜잭션이 Rollback이 이루어진다")
    public void throwChildRollbackByRequiredPropagation() {
        // then
        assertThat(userService.findAll()).hasSize(0);
        assertThat(couponService.findAll()).hasSize(0);

        // when & then
        assertThatThrownBy(() -> {
            userService.saveFailByChild(new SignUpRequest("haedoang", " 1234"));
            assertThat(userService.findAll()).hasSize(0);
            assertThat(couponService.findAll()).hasSize(0);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("[REQUIREDS_NEW] Requireds New 자식 내 예외가 발생할 경우 부모 트랜잭션과 별개로 동작한다")
    public void throwChildByRequiredsNewPropagation() {
        // given
        assertThat(userService.findAll()).hasSize(0);
        assertThat(couponService.findAll()).hasSize(0);

        // when
        userService.savedFailByChildWithRequiredsNewPropagation(new SignUpRequest("haedoang", " 1234"));

        // then
        assertThat(userService.findAll()).hasSize(1);
        assertThat(couponService.findAll()).hasSize(0)
                .as("예외의 전파는 트랜잭션의 전파와 다르다. 따라서 부모 트랜잭션에서 하위 트랜잭션의 예외를 처리해주었기 때문에 부모 데이터는 Insert가 된다");
    }

    @Test
    @DisplayName("[REQUIREDS_NEW] Requireds New 부모 예외가 발생할 경우 부모 트랜잭션만 Rollback된다")
    public void throwParentByRequiredsNewPropagation() {
        // given
        assertThat(userService.findAll()).hasSize(0);
        assertThat(couponService.findAll()).hasSize(0);

        // when
        userService.savedFailByParentWithRequiredsNewPropagation(new SignUpRequest("haedoang", " 1234"));

        // then
        assertThat(userService.findAll()).hasSize(0);
        assertThat(couponService.findAll()).hasSize(1);
    }


    @Test
    @DisplayName("중첩 트랜잭션")
    public void savedNested() {
        // given
        assertThat(userService.findAll()).hasSize(0);
        assertThat(couponService.findAll()).hasSize(0);

        // when
        userService.savedFailByChildWithNestedPropagation(new SignUpRequest("haedoang", " 1234"));

        // then
        assertThat(userService.findAll()).hasSize(1);
        assertThat(couponService.findAll()).hasSize(0);
    }


    @Test
    @DisplayName("Mandatory Propagation은 부모 트랜잭션이 존재하지 않는 경우 예외를 발생한다")
    public void mandatoryPropagationThrowsNotExistParentTransaction() {
        // when & then
        assertThatThrownBy(() -> {
            couponService.saveMandatory(1L);
        }).isInstanceOf(IllegalTransactionStateException.class)
                .as("부모 트랜잭션이 존재하지 않으면 예외를 발생한다");
    }

    @Test
    @DisplayName("Never Propagation은 비트랜잭션으로 동작한다")
    public void neverPropagationNotUseTransaction() {
        // when
        try {
            couponService.saveNeverThrowError(1L);
        } catch (Exception e) {
        }

        // then
        assertThat(couponService.findAll()).hasSize(2);
    }
}

