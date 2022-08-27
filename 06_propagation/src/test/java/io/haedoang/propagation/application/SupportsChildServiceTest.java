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
 * description :
 */
class SupportsChildServiceTest extends BaseApplicationTest {
    @Autowired
    private ParentService parentService;

    @Autowired
    private SupportsChildService childService;

    @BeforeEach
    void setUp() {
        super.setUp();
        parentService.setChildService(childService);
    }

    @Test
    @DisplayName("부모, 자식 엔티티를 등록한다")
    public void save() {
        // when
        parentService.save();

        // then
        assertThat(parentService.count()).isEqualTo(1);
        assertThat(childService.count()).isEqualTo(1);
    }

    //TODO Child Exception
}
