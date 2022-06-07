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
}
