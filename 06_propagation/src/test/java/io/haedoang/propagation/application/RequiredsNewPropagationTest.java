package io.haedoang.propagation.application;

import io.haedoang.propagation.application.ParentService;
import io.haedoang.propagation.application.RequiredsNewChildService;
import io.haedoang.propagation.infra.ParentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * author : haedoang
 * date : 2022-08-25
 * description :
 */
public class RequiredsNewPropagationTest extends BaseApplicationTest {
    private ParentService parentService;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private RequiredsNewChildService childService;

    @BeforeEach
    void setUp() {
        super.setUp();
        parentService = new ParentService(parentRepository, childService);
    }

    @Test
    @DisplayName("부모, 자식 엔티티를 등록한다 => 부모 자식이 별개의 트랜잭션으로 동작한다(로그확인)")
    public void save() {
        // when
        parentService.save();

        // then
        assertThat(parentService.count()).isEqualTo(1);
        assertThat(childService.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("REQUIREDS_NEW 는 자식 트랜잭션에서 예외가 발생할 경우 자식 트랜잭션만 롤백 처리가 된다")
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
    @DisplayName("REQUIREDS_NEW는 자식 트랜잭션에서 예외를 잡더라도 부모 트랜잭션과 별개로 동작하기 때문에 부모 트랜잭션은 롤백되지 않는다")
    public void saveFailByChildThrowExceptionWhenErrorHandling() {
        // when
        parentService.saveFailByChildThrowRuntimeExceptionCatchParentTransaction();

        // then
        assertThat(parentService.count()).isEqualTo(1L);
        assertThat(childService.count()).isEqualTo(0);
    }
}
