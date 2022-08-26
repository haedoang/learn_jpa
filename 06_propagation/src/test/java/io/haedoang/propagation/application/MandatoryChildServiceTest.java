package io.haedoang.propagation.application;

import io.haedoang.propagation.infra.ParentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * author : haedoang
 * date : 2022-08-26
 * description :
 */
//FIXME Mandatory 왜 안될까요??
class MandatoryChildServiceTest extends BaseApplicationTest {

    private ParentService parentService;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private MandatoryChildService mandatoryChildService;

    @BeforeEach
    void setUp() {
        super.setUp();
        parentService = new ParentService(parentRepository, mandatoryChildService);
    }


    @Test
    @DisplayName("부모, 자식 엔티티를 등록한다")
    public void save() {
        // when
        parentService.save();

    }

}
