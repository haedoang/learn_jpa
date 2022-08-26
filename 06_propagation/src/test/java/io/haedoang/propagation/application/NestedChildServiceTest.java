package io.haedoang.propagation.application;

import io.haedoang.propagation.infra.ParentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * author : haedoang
 * date : 2022-08-26
 * description : <a href="https://stackoverflow.com/questions/12390888/differences-between-requires-new-and-nested-propagation-in-spring-transactions">PROPAGATION NESTED VS REQUIREDS_NEW</a>
 */
class NestedChildServiceTest extends BaseApplicationTest {
    private ParentService parentService;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private NestedChildService childService;

    @BeforeEach
    void setUp() {
        super.setUp();
        parentService = new ParentService(parentRepository, childService);
    }

    @Test
    @DisplayName("부모, 자식 엔티티를 등록한다 => NESTED 트랜잭션 생성됨(로그확인)")
    public void save() {
        // when
        parentService.save();

        // then
        assertThat(parentService.count()).isEqualTo(1);
        assertThat(childService.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("NESTED는 하위 트랜잭션으로 동작하며 SAVEPOINT로 사용되어 예외가 발생할 경우 해당 트랜잭션이 롤백된다")
    public void saveFailByChildThrowException() {
        // when
        assertThatThrownBy(() -> {
            parentService.saveFailByChildThrowRuntimeException();
        }).isInstanceOf(RuntimeException.class);

        // then
        assertThat(parentService.count()).isEqualTo(1L);
        assertThat(childService.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("NESTED는 하위 트랜잭션으로 동작하기 때문에 상위 트랜잭션은 롤백처리되지 않는다")
    public void saveFailByChildThrowExceptionWhenErrorHandling() {
        // when
        parentService.saveFailByChildThrowRuntimeExceptionCatchParentTransaction();

        // then
        assertThat(parentService.count()).isEqualTo(1);
        assertThat(childService.count()).isEqualTo(0);
    }
}
