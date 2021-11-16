package jpashop;

import jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


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

            //JPQL
//            List<Member> result = em.createQuery("select m From Member m where m.name like '%kim%'", Member.class).getResultList();
//            result.stream().forEach(System.out::println);

            //Criteria 동적 쿼리 작성이 유용함. 비추천
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);
//            Root<Member> m = query.from(Member.class);
//            CriteriaQuery<Member> criteriaQuery = query.select(m).where(cb.equal(m.get("name"), "kim"));
//
//            List<Member> resultList = em.createQuery(criteriaQuery).getResultList();
//            resultList.forEach(System.out::println);

            //native query 사용
//            em.createNativeQuery(
//                    "SELECT ID FROM MEMBER"
//            ).getResultList();


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
