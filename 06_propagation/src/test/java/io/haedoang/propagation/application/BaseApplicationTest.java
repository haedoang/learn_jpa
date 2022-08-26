package io.haedoang.propagation.application;

import io.haedoang.propagation.utils.DBCleanUp;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * author : haedoang
 * date : 2022-08-26
 * description :
 */
@SpringBootTest
public class BaseApplicationTest {

    @Autowired
    private DBCleanUp dbCleanUp;

    @BeforeEach
    void setUp() {
        dbCleanUp.afterPropertiesSet();
        dbCleanUp.execute();
    }
}
