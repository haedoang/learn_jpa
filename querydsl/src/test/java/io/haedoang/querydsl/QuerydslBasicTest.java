package io.haedoang.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.haedoang.querydsl.dto.MemberDto;
import io.haedoang.querydsl.dto.QMemberDto;
import io.haedoang.querydsl.dto.UserDto;
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
    @DisplayName("????????? ????????? ????????? ????????????")
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
        QMember m = new QMember("m"); //?????? ???????????? ???????????? ????????? ?????? ?????????

        // when
        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1")) // ???????????? ????????? ??????
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
    @DisplayName("??????")
    public void resultFetch() {
        // when
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();

        Member fetchOne = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        Member fetchFirst = queryFactory
                .selectFrom(member)
                .fetchFirst();

        QueryResults<Member> fetchResults = queryFactory
                .selectFrom(member)
                .fetchResults();

        List<Member> content = fetchResults.getResults();
        long total = fetchResults.getTotal();

        // then
        assertThat(fetch).hasSize(4).as("?????? ??????");
        assertThat(fetchOne.getUsername()).isEqualTo("member1").as("?????? ??????, unique ???????????? ????????????");
        assertThat(fetchFirst.getUsername()).isEqualTo("member1").as("?????? ?????? limit 1");
        assertThat(content).hasSize(4).as("content");
        assertThat(total).isEqualTo(4).as("?????? ?????? ??????, query 2??? ????????????");

    }


    @Test
    @DisplayName("?????? ?????? ????????????, ?????? ?????? ????????????, ?????? ????????? ????????? ???????????? ??????")
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
    @DisplayName("?????? ????????? ??? ?????? ?????? ????????? ?????????")
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
    @DisplayName("TeamA??? ????????? ?????? ??????")
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
    @DisplayName("?????? ??????")
    public void thetaJoin() {
        // given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        // when
        final List<Member> members = queryFactory
                .select(member)
                .from(member, team) //???????????????????????? ???????????? ??????
                .where(member.username.eq(team.name))
                .fetch();

        // then
        assertThat(members).extracting("username")
                .containsExactly("teamA", "teamB");
    }

    @Test
    @DisplayName("????????? ?????? ??????, ??? ????????? teamA??? ?????? ??????, ????????? ?????? ??????")
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
    @DisplayName("??????????????? ?????? ????????? ?????? ?????? - ?????? ????????? ??? ????????? ?????? ?????? ?????? ???")
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
                .on(member.username.eq(team.name)) //hibernate 5.1 ???????????? ?????? ????????? ?????? ????????? ?????? ?????? ?????? ??????
                .fetch();

        assertThat(result).hasSize(7);
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    @DisplayName("?????? ??????")
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
    @DisplayName("???????????? - ????????? ?????? ?????? ??????")
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
    @DisplayName("???????????? - ?????? ???????????? ??? ??????")
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
    @DisplayName("???????????? - 10????????? ??? ??????")
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
    @DisplayName("select??? ????????????")
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
    @DisplayName("?????????: case")
    public void basicCase() {
        // when
        final List<String> result = queryFactory
                .select(member.age
                        .when(10).then("??????")
                        .when(20).then("?????????")
                        .otherwise("??????")
                )
                .from(member)
                .fetch();

        // then
        assertThat(result).containsExactly("??????", "?????????", "??????", "??????");
    }

    @Test
    @DisplayName("caseBuilder")
    public void caseBuilder() {
        // when
        final List<String> result = queryFactory
                .select(
                        new CaseBuilder()
                                .when(member.age.between(0, 20)).then("0~20???")
                                .otherwise("??????")
                )
                .from(member)
                .fetch();

        // then
        assertThat(result).containsExactly("0~20???", "0~20???", "??????", "??????");
    }

    @Test
    @DisplayName("constant ?????? ??????")
    public void constant() {
        // when
        final List<Tuple> result = queryFactory.select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        // then
        assertThat(result).extracting(it -> it.get(1, String.class)).contains("A");
    }

    @Test
    @DisplayName("concat ????????? ?????????")
    public void concat() {
        // when
        final String result = queryFactory
                .select(
                        member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();
        // then
        assertThat(result).isEqualTo("member1_10");
    }

    @Test
    @DisplayName("projection: simple")
    public void simpleProjection() {
        // when
        final String result = queryFactory
                .select(member.username)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        // then
        assertThat(result).isEqualTo("member1");
    }

    @Test
    @DisplayName("projection: tuple(repository) ???????????? ????????? ??????")
    public void tupleProjection() {
        // when
        final List<Tuple> result = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        // then
        for (Tuple tuple : result) {
            final String username = tuple.get(member.username);
            final Integer age = tuple.get(member.age);
            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }

        assertThat(result).extracting(it -> it.get(0, String.class))
                .containsExactly("member1", "member2", "member3", "member4");
        assertThat(result).extracting(it -> it.get(1, Integer.class))
                .containsExactly(10, 20, 30, 40);
    }

    @Test
    @DisplayName("dto??? ????????????1. new ????????? ??????")
    public void findDtoBYJPQL() {
        // when
        final List<MemberDto> resultList =
                em.createQuery("select new io.haedoang.querydsl.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                        .getResultList();

        // then
        assertThat(resultList).extracting("username").containsExactly("member1", "member2", "member3", "member4");
        assertThat(resultList).extracting("age").containsExactly(10, 20, 30, 40)
                .as("??????????????? ??? ?????????????????? ????????? ??????");
    }

    @Test
    @DisplayName("dto??? ????????????2. setter??? ??????")
    public void findDtoBySetter() {
        // when
        final List<MemberDto> result = queryFactory.select(Projections.bean(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();

        // then
        assertThat(result).extracting("username").containsExactly("member1", "member2", "member3", "member4");
        assertThat(result).extracting("age").containsExactly(10, 20, 30, 40);
    }

    @Test
    @DisplayName("dto??? ????????????3. field ??????")
    public void findDtoByField() {
        // when
        final List<MemberDto> result = queryFactory.select(Projections.fields(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();

        // then
        assertThat(result).extracting("username").containsExactly("member1", "member2", "member3", "member4");
        assertThat(result).extracting("age").containsExactly(10, 20, 30, 40);
    }

    @Test
    @DisplayName("dto??? ????????????4. constructor ??????")
    public void findDtoByConstructor() {
        // when
        final List<MemberDto> result = queryFactory.select(
                        Projections.constructor(
                                MemberDto.class,
                                member.username,
                                member.age
                                //,member.id ????????? ???????????? ????????? ????????? (@QueryProjection ??? ??????)
                        )
                )
                .from(member)
                .fetch();

        // then
        assertThat(result).extracting("username").containsExactly("member1", "member2", "member3", "member4");
        assertThat(result).extracting("age").containsExactly(10, 20, 30, 40);
    }

    @Test
    @DisplayName("?????????????????? ?????? ?????? ?????? ??????: 1)as 2) ExpressionUtils")
    public void asAndExpressionUtils() {
        //given
        final QMember memberSub = new QMember("memberSub");

        // when
        final List<UserDto> result = queryFactory.select(
                        Projections.fields(
                                UserDto.class,
                                member.username.as("name"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(memberSub.age.max())
                                                .from(memberSub), "age"
                                )))
                .from(member)
                .fetch();

        // then
        assertThat(result).extracting("name").containsExactly("member1", "member2", "member3", "member4");
        assertThat(result).extracting("age").contains(40);
    }

    @Test
    @DisplayName("queryProjection :: @QueryProjection ??????")
    public void queryProjection() {
        // when
        final MemberDto result = queryFactory.select(new QMemberDto(member.username, member.age))
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        // then
        assert result != null;
        assertThat(result.getUsername()).isEqualTo("member1");
        assertThat(result.getAge()).isEqualTo(10)
                .as("typeSafety ??????, compile ???????????? ????????? ????????? ??? ?????? ??????")
                .as("??????1) Dto??? queryDsl??? ?????? ????????? ????????? ??????");
    }

    @Test
    @DisplayName("?????? ??????: distinct")
    public void distinct() {
        // given
        em.persist(new Member("member1", 50));

        // when
        final String result = queryFactory
                .select(member.username).distinct()
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        // then
        assertThat(result).isEqualTo("member1");
    }

    @Test
    @DisplayName("???????????? : booleanBuilder")
    public void booleanBuilder() {
        // given
        String usernameParam = "member1";
        Integer ageParam = 10;

        // when
        List<Member> result = searchMember1(usernameParam, ageParam);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("???????????? : whereParam")
    public void whereParam() {
        // given
        String usernameParam = "member1";
        Integer ageParam = 10;

        // when
        List<Member> result = searchMember2(usernameParam, ageParam);

        // then
        assertThat(result).hasSize(1);
    }

    /**
     * 1) BooleanBuilder
     *
     * @param usernameCond
     * @param ageCond
     * @return
     */
    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (usernameCond != null) {
            booleanBuilder.and(member.username.eq(usernameCond));
        }

        if (ageCond != null) {
            booleanBuilder.and(member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member)
                .where(booleanBuilder)
                .fetch();
    }

    /**
     * 2) whereParam
     *
     * @param usernameCond
     * @param ageCond
     * @return
     */
    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                .where(allEq(usernameCond, ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    /**
     * ?????? ?????? ????????? ==> ???????????? ??????
     */
    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    @Test
    //@Commit
    @DisplayName("?????? ??????")
    public void bulkUpdate1() {
        // when
        final long count = queryFactory
                .update(member)
                .set(member.username, "?????????")
                .where(member.age.lt(28))
                .execute();

        // then
        final Member result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(10))
                .fetchOne();


        assert result != null;
        assertThat(result.getUsername()).isEqualTo("member1");
        assertThat(count).isEqualTo(2)
                .as("?????? ????????? ????????? ??????????????? ???????????? DB??? ?????? ???????????? ????????? ????????? ???????????? ?????? ??? ??????");
    }

    @Test
    //@Commit
    @DisplayName("?????? ?????? ??? ????????? ??????????????? ??????????????? ??????")
    public void bulkUpdate2() {
        // when
        final long count = queryFactory
                .update(member)
                .set(member.username, "?????????")
                .where(member.age.lt(28))
                .execute();

        em.flush();
        em.clear();

        // then
        final Member result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(10))
                .fetchOne();

        assert result != null;
        assertThat(result.getUsername()).isEqualTo("?????????");
        assertThat(count).isEqualTo(2)
                .as("?????? ?????? ??? ????????? ??????????????? ???????????? ????????? ???????????? ???????????? ??????");
    }

    @Test
    @DisplayName("bulk add, multiply, delete ?????? ????????? ????????????")
    public void bulkAdd() {
        // when
        final long count = queryFactory
                .update(member)
                .set(member.age, member.age.add(1))
                .execute();

        // then
        em.flush();
        em.clear();

        final List<Member> result = queryFactory.selectFrom(member)
                .fetch();

        assertThat(result).extracting("age").containsExactly(11, 21, 31, 41);
    }

    @Test
    @DisplayName("")
    public void sqlFunction() {
        // when
        final List<String> result = queryFactory
                .select(
                        Expressions.stringTemplate("function('replace',{0}, {1}, {2})",
                                member.username, "member", "M"))
                .from(member)
                .fetch();

        // then
        assertThat(result).containsExactly("M1", "M2", "M3", "M4")
                .as("org.hibernate.dialect.H2Dialect ??? ??????????????? ???");
    }

    @Test
    @DisplayName("")
    public void sqlFunction2() {
        // when
        final List<String> result = queryFactory
                .select(member.username)
                .from(member)
                //.where(member.username.eq(Expressions.stringTemplate("function('lower', {0})", member.username)))
                .where(member.username.eq(member.username.lower())) // ??????????????? ??????????????? lower ??????
                .fetch();

        // then
        assertThat(result).containsExactly("member1", "member2", "member3", "member4");
    }
}

