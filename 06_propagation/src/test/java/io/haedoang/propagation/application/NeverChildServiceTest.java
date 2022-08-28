package io.haedoang.propagation.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.IllegalTransactionStateException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * author : haedoang
 * date : 2022-08-26
 * description :
 */
class NeverChildServiceTest extends BaseApplicationTest {
    @Autowired
    private ParentService parentService;

    @Autowired
    private NeverChildService childService;

    @BeforeEach
    void setUp() {
        super.setUp();
        parentService.setChildService(childService);
    }

    @Test
    @DisplayName("부모 트랜잭션이 존재하는 경우 예외가 발생한다")
    public void saveFail() {
        // when & then
        assertThatThrownBy(() -> parentService.save())
                .isInstanceOf(IllegalTransactionStateException.class)
                .as("상위 트랜잭션이 존재하기 때문에 예외가 발생한다");
    }

    @Test
    @DisplayName("부모 트랜잭션이 없는 경우 Non Transaction으로 동작한다")
    public void saveWithoutTransaction() {
        // when
        parentService.saveWithoutTransaction();

        // then
        assertThat(parentService.count()).isEqualTo(1);
        assertThat(childService.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("자식 트랜잭션이 비트랜잭션으로 동작하여 예외가 발생해도 더티 데이터가 남는다")
    public void throwChildException() {
        // when
        assertThatThrownBy(() -> parentService.saveFailByChildThrowRuntimeExceptionWithoutTransaction())
                .isInstanceOf(RuntimeException.class);

        // then
        assertThat(parentService.count()).isEqualTo(1);
        assertThat(childService.count()).isEqualTo(1);
    }

}
