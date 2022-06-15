package io.haedoang.soft_delete.다대일양방향;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @DisplayName("생성하기")
    public void create() {
        // given
        Team teamA = Team.valueOf("teamA");
        Team teamB = Team.valueOf("teamB");

        User user1 = User.valueOf("user1", 1);
        User user2 = User.valueOf("user2", 2);
        User user3 = User.valueOf("user3", 3);

        // when
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        user1.addTeam(teamA);
        user2.addTeam(teamA);
        user3.addTeam(teamB);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // then
        assertThat(teamA.getId()).isNotNull();
        assertThat(teamB.getId()).isNotNull();
        assertThat(user1.getTeam()).isEqualTo(teamA);
        assertThat(user2.getTeam()).isEqualTo(teamA);
        assertThat(user3.getTeam()).isEqualTo(teamB);


        // when
        List<Team> teams = teamRepository.findAll();
        teams.forEach(it -> {
            if (it.getName().equals(teamA.getName())) {
                assertThat(it.getUsers()).hasSize(2);
            } else {
                assertThat(it.getUsers()).hasSize(1);
            }
        });
    }

    @Test
    @DisplayName("삭제하기")
    public void delete() {
        // given
        Team teamA = Team.valueOf("teamA");
        Team teamB = Team.valueOf("teamB");

        User user1 = User.valueOf("user1", 1);
        User user2 = User.valueOf("user2", 2);
        User user3 = User.valueOf("user3", 3);

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        user1.addTeam(teamA);
        user2.addTeam(teamA);
        user3.addTeam(teamB);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        //when
        teamRepository.deleteById(teamA.getId());
        List<User> teamUsers = userRepository.findByTeamId(teamA.getId());

        // then
        assertThat(teamUsers).hasSize(2);

        // when
        teamUsers.forEach(User::removeTeam);

        // then
        assertThat(userRepository.findByTeamId(teamA.getId())).isEmpty();
    }
}
