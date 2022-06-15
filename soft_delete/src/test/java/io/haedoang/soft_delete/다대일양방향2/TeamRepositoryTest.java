package io.haedoang.soft_delete.다대일양방향2;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * fileName : TeamRepositoryTest
 * author : haedoang
 * date : 2022-06-15
 * description :
 */
@DataJpaTest
class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("생성하기")
    public void create() {
        Team teamA = Team.valueOf("teamA");
        Team teamB = Team.valueOf("teamB");

        User user1 = User.valueOf("user1", 1);
        User user2 = User.valueOf("user2", 2);
        User user3 = User.valueOf("user3", 3);

        teamA.addUser(user1);
        teamA.addUser(user2);
        teamB.addUser(user3);

        // when
        teamRepository.saveAll(Lists.newArrayList(teamA, teamB));

        // then
        assertThat(teamRepository.findAll()).hasSize(2);
        assertThat(userRepository.findAll()).hasSize(3);
    }

    //TODO 삭제구현
}
