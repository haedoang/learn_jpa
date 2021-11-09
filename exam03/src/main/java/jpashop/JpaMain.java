package jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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


//            Order order = new Order();
//            order.setStatus(OrderStatus.ORDER);
//            em.persist(order);
//
//            Member m = new Member();
//            m.setName("memberA");
//            m.getOrders().add(order);
//            em.persist(m);



            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
