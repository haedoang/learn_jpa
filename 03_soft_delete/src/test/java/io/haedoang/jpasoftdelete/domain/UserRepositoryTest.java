package io.haedoang.jpasoftdelete.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * author : haedoang
 * date : 2022/06/07
 * description :
 */
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(User.valueOf("saveduser", 1));
    }

    @Test
    @DisplayName("생성하기")
    public void create() {
        // given
        final User user = User.valueOf("haedoang", 34);

        // when
        userRepository.save(user);

        // then
        assertThat(user.getId()).isNotNull();
        assertThat(user.getCreatedAt().isEqual(user.getUpdatedAt())).isTrue();
    }

    @Test
    @DisplayName("updateDate 테스트")
    public void updateDateTest() {
        // given
        final User savedUser = userRepository.findAll().get(0);
        final LocalDateTime updatedAt = savedUser.getUpdatedAt();
        // when
        savedUser.update(User.valueOf("updateUser", 2));
        userRepository.flush();

        // then
        assertThat(updatedAt.isBefore(savedUser.getUpdatedAt()));
    }

}