package io.haedoang.propagation;

import io.haedoang.propagation.application.ChildService;
import io.haedoang.propagation.application.ParentService;
import io.haedoang.propagation.application.RequiredsNewChildService;
import io.haedoang.propagation.infra.ParentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * author : haedoang
 * date : 2022-08-25
 * description :
 */
@SpringBootTest
public class RequiredsNewPropagationTest {
    private ParentService parentService;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private RequiredsNewChildService childService;

    @BeforeEach
    void setUp() {
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
}
