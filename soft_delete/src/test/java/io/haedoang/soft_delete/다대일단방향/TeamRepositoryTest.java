package io.haedoang.soft_delete.다대일단방향;

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

    /**
     * insert teamA
     * insert teamB
     * insert user1
     * insert user2
     * insert user3
     */
    @Test
    @DisplayName("다대일 단방향 생성하기")
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
    }

    /***
     *  delete Team teamA
     *  Select User where team_id = teamA.id
     *  delete User user1
     *  delete User user2
     */
    @Test
    @DisplayName("다대일 단방향 삭제")
    public void delete() {
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

        // when
        teamRepository.deleteById(teamA.getId());
        List<User> teamUsers = userRepository.findByTeamId(teamA.getId());

        // then
        assertThat(teamRepository.findAll()).hasSize(1);
        assertThat(teamUsers).hasSize(2);
        assertThat(teamUsers).extracting(User::getTeam)
                .extracting(Team::getId).contains(teamA.getId())
                .as("삭제된 팀의 정보가 회원 엔티티에 남아있다. 삭제를 추가로 해주어야 한다.");

        // when
        teamUsers.stream().forEach(User::removeTeam);

        // then
        assertThat(userRepository.findByTeamId(teamA.getId())).isEmpty();
    }
}
