package io.haedoang.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.haedoang.querydsl.entity.Member;
import io.haedoang.querydsl.entity.QMember;
import io.haedoang.querydsl.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

import static io.haedoang.querydsl.entity.QMember.member;
import static io.haedoang.querydsl.entity.QTeam.team;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName : io.haedoang.querydsl
 * fileName : QuerydslBasicTest
 * author : haedoang
 * date : 2022-05-03
 * description :
 */
@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(em);
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
    }

    @Test
    @DisplayName("런타임 시점에 오류를 검증한다")
    public void startJPQL() {
        // given
        String query = "select m from Member m where m.username = :username";

        // when
        Member findMember = em.createQuery(query, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }


    @Test
    public void startQuerydsl() {
        // given
        QMember m = new QMember("m"); //같은 테이블을 조인할떄 구분을 위해 사용함

        // when
        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1")) // 파라미터 바인딩 처리
                .fetchOne();

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void search() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1")
                                .and(member.age.between(10, 30)))
                .fetchOne();

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void searchAndParam() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1"),
                        member.age.goe(10))
                .fetchOne();

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }


    @Test
    @DisplayName("회원 나이 내림차순, 회원 이름 올림차순, 회원 이름이 없으면 마지막에 출력")
    public void sort() {
        // given
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        // when
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        // then
        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }


    @Test
    @DisplayName("pagingQuery")
    public void paging() {
        // when
        QueryResults<Member> queryResults = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        // then
        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(2);
    }


    @Test
    @DisplayName("")
    public void aggregation() {
        // given
        List<Tuple> result = queryFactory
                .select(member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min())
                .from(member)
                .fetch();

        // when
        Tuple tuple = result.get(0);

        // then
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(120);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    @Test
    @DisplayName("팀의 이름과 각 팀의 평균 연령을 구해라")
    public void group() {
        // given
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        // when
        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        // then
        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }

    @Test
    @DisplayName("TeamA에 소속된 모든 회원")
    public void join() {
        // given
        final List<Member> members = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        // then
        assertThat(members).hasSize(2);
        assertThat(members).extracting(Member::getUsername)
                .containsExactly("member1", "member2");
    }

    @Test
    @DisplayName("세타 조인")
    public void thetaJoin() {
        // given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        // when
        final List<Member> members = queryFactory
                .select(member)
                .from(member, team) //막조인이라고도함 외부조인 불가
                .where(member.username.eq(team.name))
                .fetch();

        // then
        assertThat(members).extracting("username")
                .containsExactly("teamA", "teamB");
    }

    @Test
    @DisplayName("회원과 팀을 조인, 팀 이름이 teamA인 팀만 조회, 회원은 모두 조회")
    public void joinOnFiltering() {
        // when
        final List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                //.where(team.name.eq("teamA"))
                .fetch();

        // then
        assertThat(result).hasSize(4);
    }

    @Test
    @DisplayName("연관관계가 없는 엔티티 외부 조인 - 회원 이름과 팀 이름이 같은 대상 외부 조")
    public void joinOnNoRelation() {
        // given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        // when
        final List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team)
                .on(member.username.eq(team.name)) //hibernate 5.1 이상부터 서로 관계가 없는 필드로 외부 조인 기능 추가
                .fetch();

        assertThat(result).hasSize(7);
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    @DisplayName("페치 조인")
    public void fetchJoin() {
        // given
        em.flush();
        em.clear();

        // when
        final Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        final boolean result = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

        // then
        assertThat(result).isFalse();

        // when
        final Member findMemberFetch = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        final boolean result2 = emf.getPersistenceUnitUtil().isLoaded(findMemberFetch.getTeam());


        // then
        assertThat(result2).isTrue();
    }

    @Test
    @DisplayName("서브쿼리 - 나이가 가장 많은 회원")
    public void subQuery() {
        // given
        final QMember memberSub = new QMember("memberSub");

        // when
        final List<Member> members = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();


        // then
        assertThat(members).hasSize(1);
        assertThat(members).extracting("age").contains(40);
    }

    @Test
    @DisplayName("서브쿼리 - 평균 나이보다 큰 회원")
    public void subQueryGoe() {
        // given
        final QMember memberSub = new QMember("memberSub");

        // when
        final List<Member> members = queryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        // then
        assertThat(members).hasSize(2);
        assertThat(members).extracting("age").containsExactly(30, 40);
    }


    @Test
    @DisplayName("서브쿼리 - 10살보다 큰 회원")
    public void subQueryIn() {
        // given
        final QMember memberSub = new QMember("memberSub");

        // when
        final List<Member> members = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        JPAExpressions
                                .select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        // then
        assertThat(members).hasSize(3);
        assertThat(members).extracting("age").containsExactly(20, 30, 40);
    }

    @Test
    @DisplayName("select절 서브쿼리")
    public void selectSubQuery() {
        // given
        final QMember memberSub = new QMember("memberSub");

        // when
        final List<Tuple> result = queryFactory
                .select(
                        member.username,
                        JPAExpressions
                                .select(memberSub.age.avg().as("avg"))
                                .from(memberSub))
                .from(member)
                .fetch();

        // then
        assertThat(result).hasSize(4);
        assertThat(result).extracting(it -> it.get(1, Double.class)).contains(25.0);
    }

    @Test
    @DisplayName("조건문: case")
    public void basicCase() {
        // when
        final List<String> result = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타")
                )
                .from(member)
                .fetch();

        // then
        assertThat(result).containsExactly("열살", "스무살", "기타", "기타");
    }

    @Test
    @DisplayName("caseBuilder")
    public void caseBuilder() {
        // when
        final List<String> result = queryFactory
                .select(
                        new CaseBuilder()
                                .when(member.age.between(0, 20)).then("0~20살")
                                .otherwise("기타")
                )
                .from(member)
                .fetch();

        // then
        assertThat(result).containsExactly("0~20살", "0~20살", "기타", "기타");
    }
}

