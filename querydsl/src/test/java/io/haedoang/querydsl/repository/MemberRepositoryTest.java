package io.haedoang.querydsl.repository;

import io.haedoang.querydsl.dto.MemberSearchCondition;
import io.haedoang.querydsl.dto.MemberTeamDto;
import io.haedoang.querydsl.entity.Member;
import io.haedoang.querydsl.entity.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName : io.haedoang.querydsl.repository
 * fileName : MemberRepositoryTest
 * author : haedoang
 * date : 2022-05-19
 * description :
 */
@SpringBootTest
@Transactional
public class MemberRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("jpaRepository 테스트")
    public void basicTest() {
        // given
        Member member = new Member("member1", 10);
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findById(member.getId()).get();

        // then
        assertThat(findMember).isEqualTo(member);

        // when
        List<Member> result1 = memberRepository.findAll();

        // then
        assertThat(result1).contains(member);

        // when
        List<Member> result2 = memberRepository.findByUsername("member1");

        // then
        assertThat(result2).contains(member);
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
        final List<MemberTeamDto> actual = memberRepository.search(condition);

        // then
        assertThat(actual).extracting("username").contains("member3", "member4");
    }


    @Test
    @DisplayName("")
    public void searchPageSimple() {
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

        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<MemberTeamDto> actual = memberRepository.searchPageSimple(new MemberSearchCondition(), pageRequest);

        assertThat(actual.getSize()).isEqualTo(3);
        assertThat(actual.getContent()).extracting("username")
                .containsExactly("member1", "member2", "member3")
                .as("count, list query 2개");

    }
}
