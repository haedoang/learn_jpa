package io.haedoang.querydsl.repository;

import io.haedoang.querydsl.dto.MemberSearchCondition;
import io.haedoang.querydsl.dto.MemberTeamDto;
import io.haedoang.querydsl.entity.Member;
import io.haedoang.querydsl.entity.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * packageName : io.haedoang.querydsl.repository
 * fileName : MemberRepositoryTest
 * author : haedoang
 * date : 2022-05-13
 * description :
 */
@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberJpaRepository memberJpaRepository;


    @Test
    @DisplayName("jpaRepository 테스트")
    public void basicTest() {
        // given
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        // when
        Member findMember = memberJpaRepository.findById(member.getId()).get();

        // then
        assertThat(findMember).isEqualTo(member);

        // when
        List<Member> result1 = memberJpaRepository.findAll();

        // then
        assertThat(result1).contains(member);

        // when
        List<Member> result2 = memberJpaRepository.findByUsername("member1");

        // then
        assertThat(result2).contains(member);
    }

    @Test
    @DisplayName("querydsl test")
    public void queryDslTest() {
        // given
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        // when
        List<Member> result1 = memberJpaRepository.findAll_queryDsl();

        // then
        assertThat(result1).contains(member);

        // when
        List<Member> result2 = memberJpaRepository.findByUsername_queryDsl("member1");

        // then
        assertThat(result2).contains(member);
    }

    @Test
    @DisplayName("동적 쿼리 테스트1: builder")
    public void builderTest1() {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        final MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        // when
        final List<MemberTeamDto> actual = memberJpaRepository.searchByBuilder(condition);

        // then
        assertThat(actual).extracting("username").contains("member4");
    }

    @Test
    @DisplayName("동적 쿼리 테스트2: builder")
    public void builderTest2() {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        final MemberSearchCondition condition = new MemberSearchCondition();
        condition.setTeamName("teamB");

        // when
        final List<MemberTeamDto> actual = memberJpaRepository.searchByBuilder(condition);

        // then
        assertThat(actual).extracting("username").contains("member3", "member4");
    }

    @Test
    @DisplayName("동적 쿼리 테스트3 - 조건이 없는 쿼리:builder")
    public void builderTest3() {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        final MemberSearchCondition condition = new MemberSearchCondition();

        // when
        final List<MemberTeamDto> actual = memberJpaRepository.searchByBuilder(condition);

        // then
        assertThat(actual).extracting("username").contains("member1", "member2", "member3", "member4");
    }

    @Test
    @DisplayName("동적 쿼리 테스트1: whereParam")
    public void searchWhereParam() {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        final MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        // when
        final List<MemberTeamDto> actual = memberJpaRepository.search(condition);

        // then
        assertThat(actual).extracting("username").contains("member4");
    }

    @Test
    @DisplayName("동적 쿼리 테스트2: whereParam")
    public void searchWhereParam2() {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        final MemberSearchCondition condition = new MemberSearchCondition();
        condition.setTeamName("teamB");

        // when
        final List<MemberTeamDto> actual = memberJpaRepository.search(condition);

        // then
        assertThat(actual).extracting("username").contains("member3", "member4");
    }

    @Test
    @DisplayName("동적 쿼리 테스트3: whereParam")
    public void searchWhereParam3() {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        final MemberSearchCondition condition = new MemberSearchCondition();

        // when
        final List<MemberTeamDto> actual = memberJpaRepository.search(condition);

        // then
        assertThat(actual).extracting("username").contains("member1", "member2", "member3", "member4");
    }
}
