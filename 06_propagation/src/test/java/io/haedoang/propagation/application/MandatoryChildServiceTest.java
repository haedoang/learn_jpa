package io.haedoang.propagation.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author : haedoang
 * date : 2022-08-26
 * description :
 */
//FIXME Mandatory 왜 안될까요??
class MandatoryChildServiceTest extends BaseApplicationTest {
    @Autowired
    private ParentService parentService;

    @Autowired
    private MandatoryChildService mandatoryChildService;

    @BeforeEach
    void setUp() {
        super.setUp();
        parentService.setChildService(mandatoryChildService);
    }


    @Test
    @DisplayName("부모, 자식 엔티티를 등록한다")
    public void save() {
        // when
        parentService.save();

    }

}
