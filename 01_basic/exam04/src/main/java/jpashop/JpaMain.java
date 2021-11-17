package jpashop;

import jpashop.domain.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

/**
 * packageName : jpashop
 * fileName : JpaMain
 * author : haedoang
 * date : 2021-11-09
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

            Movie movie = new Movie();
            movie.setActor("actor");
            movie.setDirector("director");
            movie.setName("winds");
            movie.setPrice(10000);
            movie.setCreatedBy("haedoang");
            movie.setCreatedDate(LocalDateTime.now());
            movie.setLastModifiedBy("haedoang");
            movie.setLastModifiedDate(LocalDateTime.now());
            em.persist(movie);

//            Team team = new Team();
//            team.setName("teamA");
//            team.setCreatedBy("haedoang");
//            team.setCreatedDate(LocalDateTime.now());
//            team.setLastModifiedBy("haedoang");
//            team.setLastModifiedDate(LocalDateTime.now());

//            em.persist(team);



            em.flush();
            em.clear();

//            Item item = em.find(Item.class, movie.getId());
//            System.out.println("item = " + item);


            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
