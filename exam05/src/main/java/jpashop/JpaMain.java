package jpashop;

import jpashop.domain.Member;
import jpashop.domain.Team;

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

            Member member = new Member();
            member.setUsername("hello");
            em.persist(member);

            em.flush();
            em.clear();


            Member findMember = em.getReference(Member.class, member.getId());
            System.out.println(findMember.getClass());
//            em.detach(findMember);
//            em.close();
//            em.clear();
            //프록시 초기화 확인
//            System.out.println(emf.getPersistenceUnitUtil().isLoaded(findMember)); //false
//            findMember.getUsername();
//            System.out.println(emf.getPersistenceUnitUtil().isLoaded(findMember)); //true
            //프록시 클래스 확인 방법
            //findMember.getClass().getName()
            //프록시 강제 초기화
            org.hibernate.Hibernate.initialize(findMember);


            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }


}
