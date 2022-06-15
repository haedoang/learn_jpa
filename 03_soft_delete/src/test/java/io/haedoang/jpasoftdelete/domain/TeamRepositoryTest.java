package io.haedoang.jpasoftdelete.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * fileName : TeamRepositoryTest
 * author : haedoang
 * date : 2022-06-07
 * description :
 */
@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @DisplayName("team 생성하기")
    public void create() {
        // given
        Team teamA = Team.valueOf("teamA");
        Team teamB = Team.valueOf("teamB");

        // when
        teamA.addUser(User.valueOf("user1", 1));
        teamA.addUser(User.valueOf("user2", 2));
        teamB.addUser(User.valueOf("user3", 3));
        teamRepository.saveAll(Lists.newArrayList(teamA, teamB));
        List<Team> actual = teamRepository.findAll();

        // then
        assertThat(actual).extracting("name").contains("teamA", "teamB");
        assertThat(actual.stream().flatMap(it ->
                        it.getUsers().stream())
                .collect(Collectors.toList()))
                .extracting("name").contains("user1", "user2", "user3");
    }

    @Test
    @DisplayName("team 삭제하기")
    public void delete() {
        // given
        Team teamA = Team.valueOf("teamA");
        teamA.addUser(User.valueOf("user1", 1));
        teamA.addUser(User.valueOf("user2", 2));
        teamA.addUser(User.valueOf("user3", 3));
        teamRepository.save(teamA);

        // when
        teamRepository.flush();
        teamRepository.deleteById(teamA.getId());

        // then
        assertThat(teamRepository.findAll()).hasSize(0);

        /**
         *  query result
         *  case1 양방향 연관관계
         *
         *  1. update User user1 deleted = true
         *  2. update User user2 deleted = true
         *  3. update User user3 deleted = true
         *  4. update Team teamA deleted = true
         *
         *  ==> 하위 엔티티 N개 쿼리 발생
         *
         *  case2 단방향 연관관계  Team --- User (연관관계 주인)
         *
         *  1. update User user1 set team_id = null
         *  2. update User user2  set team_id = null
         *  3. update User user3 set team_id = null
         *  4. update User user1 deleted = true
         *  5. update User user2 deleted = true
         *  6. update User user3 deleted = true
         *  7. update Team teamA deleted = true
         */
    }
}
