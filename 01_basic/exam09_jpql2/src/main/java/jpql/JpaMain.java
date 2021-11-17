package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

/**
 * packageName : jpql
 * fileName : JpaMain
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member1 = new Member();
//            member1.setUsername("관리자1");
//            member1.setTeam(team);
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setUsername("관리자2");
//            member2.setTeam(team);
//            em.persist(member2);
//
//            em.flush();
//            em.clear();
//
//            //String query = "select m.username from Member m";
//            //String query = "select m.team from Member m"; // 묵시적 join
//            String query = "select t.members from Team t";
//
//            List<Collection> resultList = em.createQuery(query, Collection.class).getResultList();
//            for (Object o : resultList) {
//                System.out.println("o = " + o);
//            }


            //FETCH JOIN!! 매우 중요함!!
            Team team1 = new Team();
            team1.setName("팀A");
            em.persist(team1);
            Team team2 = new Team();
            team2.setName("팀B");
            em.persist(team2);
            Team team3 = new Team();
            team3.setName("팀C");
            em.persist(team3);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(team1);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(team2);
            em.persist(member3);

//            Member member4 = new Member();
//            member4.setUsername("회원4");
//            em.persist(member4);

            em.flush();
            em.clear();

            //페치 조인 사용을 안할 떄
            //String query = "select m from Member m";
//            String query = "select m from Member m join fetch m.team";
//            List<Member> resultList = em.createQuery(query, Member.class).getResultList();
//
//            for (Member member : resultList) {
//                System.out.println("member.getUsername() = " + member.getUsername() + ", " + member.getTeam().getName());
//            }
            //쿼리 실행 시
            //회원1, 팀A(SQL)
            //회원2, 팀A(1차캐시)
            //회원3, 팀B(SQL)
            // N + 1 문제 발생, 즉시로딩, 지연로딩 상관없이.. 발생함..

            //컬렉션 페치 조인 distinct로 줄여줄 수 있음.
//            String query = "select t From Team t";
//            List<Team> resultList = em.createQuery(query, Team.class)
//                    .setFirstResult(0)
//                    .setMaxResults(2)
//                    .getResultList();
//
//            System.out.println("resultList.size() = " + resultList.size());
//            for (Team team : resultList) {
//                System.out.println("team.getName() = " + team.getName() + "|members = " + team.getMembers().size());
//                for ( Member member : team.getMembers()) {
//                    System.out.println(" >>> member = " + member);
//                }
//            }

            //엔티티 직접 사용하기 - 기본키 - PK
//            String query = "select m from Member m where m = :member";
//            Member findMember = em.createQuery(query, Member.class)
//                    .setParameter("member", member1)
//                    .getSingleResult();
//
//            System.out.println(findMember);

            //엔티티 직접 사용하기 외래키 - PK
//            String query = "select m from Member m where m.team = :team";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .setParameter("team" , team1).getResultList();
//
//            resultList.forEach(System.out::println);

            //NamedQuery
//            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
//                    .setParameter("username", member1.getUsername()).getResultList();
//
//            resultList.forEach(System.out::println);

            //FLUSH
            //벌크 연산
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            System.out.println("resultCount = " + resultCount);
            //데이터 정확성이안맞음..  벌크 연산에서 flush를 수행했지만 member 1~3 은 age 0 값을 가지고 있음 
//            System.out.println("member1.getAge() = " + member1.getAge());
//            System.out.println("member2.getAge() = " + member2.getAge());
//            System.out.println("member3.getAge() = " + member3.getAge());
            
            //새로 가져오려면 em.clear()
            em.clear();
            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
