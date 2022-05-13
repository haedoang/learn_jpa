package io.haedoang.querydsl.repository;

import io.haedoang.querydsl.entity.Member;
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
        Member member = new Member("member1" , 10);
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
        Member member = new Member("member1" , 10);
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
}
