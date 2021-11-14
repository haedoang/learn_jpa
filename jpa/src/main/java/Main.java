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

            Member member = new Member();
            member.setName("memberA");
            member.setTeam(team);

            team.getMembers().add(member);
            em.persist(team);

            em.flush();
            em.clear();

            Team team1 = em.find(Team.class, team.getId());
            System.out.println(team1.getMembers());


            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
