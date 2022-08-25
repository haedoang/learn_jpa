package io.haedoang.propagation;

import io.haedoang.propagation.application.ChildService;
import io.haedoang.propagation.application.ParentService;
import io.haedoang.propagation.domain.Parent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * author : haedoang
 * date : 2022-08-25
 * description :
 */
@SpringBootTest
public class RequiredPropagationTest {
    @Autowired
    private ParentService parentService;

    @Autowired
    private ChildService childService;

    @Test
    @DisplayName("부모, 자식 엔티티를 등록한다")
    public void save() {
        // when
        parentService.save();

        // then
        assertThat(parentService.count()).isEqualTo(1);
        assertThat(childService.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("Required는 자식 트랜잭션에서 예외가 발생할 경우 롤백 처리된다")
    public void saveFailByChildThrowException() {
        // when
        assertThatThrownBy(() -> {
            parentService.saveFailByChildThrowRuntimeException();
        }).isInstanceOf(RuntimeException.class);

        // then
        assertThat(parentService.count()).isEqualTo(0);
        assertThat(childService.count()).isEqualTo(0);
    }

    /**
     * <a href="https://techblog.woowahan.com/2606/">우아한형제들 기술 블로그 참고</a>
     */
    @Test
    @DisplayName("Required는 자식 트랜잭션의 예외가 발생하는 경우 예외를 잡더라도 롤백 처리를 한다")
    public void saveFailByChildThrowExceptionWhenErrorHandling() {
        // when
        assertThatThrownBy(() -> parentService.saveFailByChildThrowRuntimeExceptionCatchParentTransaction())
                .isInstanceOf(UnexpectedRollbackException.class);

        // then
        assertThat(parentService.count()).isEqualTo(0);
        assertThat(childService.count()).isEqualTo(0);
    }
}
