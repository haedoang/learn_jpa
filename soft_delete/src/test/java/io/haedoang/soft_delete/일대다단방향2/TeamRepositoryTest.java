package io.haedoang.soft_delete.일대다단방향2;


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


    /**
     * insert user user1
     * insert user user2
     * insert user user3
     * insert team teamA
     * insert team teamB
     * update user user1.team_id
     * update user user2.team_id        좋지 않음
     * update user user3.team_id
     */
    @Test
    @DisplayName("생성하기")
    public void create() {
        // given
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

    /***
     *  update user1 delete=true
     *  update user2 delete=true
     *  update user3 delete=true
     *  update teamA delete=true
     *
     *  orphanRemoval = true
     *  update user 1 team_id=null
     *  update user 2 team_id=null
     *  update user 3 team_id=null
     *  update user1 delete=true
     *  update user2 delete=true
     *  update user3 delete=true
     *  update teamA delete=true
     */
    @Test
    @DisplayName("삭제하기")
    public void delete() {
        Team teamA = Team.valueOf("teamA");
        Team teamB = Team.valueOf("teamB");

        User user1 = User.valueOf("user1", 1);
        User user2 = User.valueOf("user2", 2);
        User user3 = User.valueOf("user3", 3);

        teamA.addUser(user1);
        teamA.addUser(user2);
        teamB.addUser(user3);

        teamRepository.saveAll(Lists.newArrayList(teamA, teamB));

        // when
        teamRepository.deleteById(teamA.getId());

        // then
        assertThat(teamRepository.findAll()).hasSize(1);
        assertThat(userRepository.findAll()).hasSize(1);
    }
}
