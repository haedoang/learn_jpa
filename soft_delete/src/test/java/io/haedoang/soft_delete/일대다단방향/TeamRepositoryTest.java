package io.haedoang.soft_delete.일대다단방향;

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
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

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
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        teamA.addUser(user1);
        teamA.addUser(user2);
        teamB.addUser(user3);

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        List<Team> teams = teamRepository.findAll();

        // then
        teams.forEach(team -> {
            if (team.getName().equals(teamA.getName())) {
                assertThat(team.getUsers()).hasSize(2);
            } else {
                assertThat(team.getUsers()).hasSize(1);
            }
        });
    }

    @Test
    @DisplayName("삭제하기")
    public void delete() {
        Team teamA = Team.valueOf("teamA");
        Team teamB = Team.valueOf("teamB");

        User user1 = User.valueOf("user1", 1);
        User user2 = User.valueOf("user2", 2);
        User user3 = User.valueOf("user3", 3);

        // when
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        teamA.addUser(user1);
        teamA.addUser(user2);
        teamB.addUser(user3);

        teamRepository.save(teamA);
        teamRepository.save(teamB);
        teamRepository.deleteById(teamA.getId());

        // then
        assertThat(teamRepository.findAll()).hasSize(1);
        assertThat(userRepository.findAll()).hasSize(3)
                .as("teamA 사용자 user1, user2는  고아객체로 남게된다");
    }
}
