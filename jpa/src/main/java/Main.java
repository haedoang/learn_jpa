import entity.Member;
import entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * packageName :
 * fileName : Main
 * author : haedoang
 * date : 2021/11/08
 * description :
 */
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setName("haedoang");
            member.setTeam(team);
            em.persist(member);
//            team.getMembers().add(member); 연관관계의 주인이 아닌 경우 조회만 가능하므로 insert되지 않는다.

            em.flush(); //db에 쿼리를 반영
            em.clear(); //영속성 컨텍스트 내 캐시를 비움

            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            findTeam.getName();

            List<Member> members = findTeam.getMembers();
            members.stream().forEach(System.out::println);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
