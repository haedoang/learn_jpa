package jpashop;

import jpashop.domain.item.Album;
import jpashop.domain.old.Child;
import jpashop.domain.old.Member;
import jpashop.domain.old.Parent;
import jpashop.domain.old.Team;

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

//
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("hello");
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();

// ########## RROXY EXAMPLE
//            Member findMember = em.getReference(Member.class, member.getId());
//            //디버그를 찍어보면 findMember객체의 필드들은 null값을 가지고 있다.
//            //getter를 통해서 접근할 경우 실제 객체값을 반환한다.
//            System.out.println("findMember = " + findMember);

//            프록시 객체 조회
//            System.out.println(findMember.getClass());
//
//            프록시 객체의 값을 영속성 컨텍스트가 비워졌을 때 접근하려고 하면 LazyInitializationException 예외가 발생한다.
//            em.detach(findMember);
//            em.close();
//            em.clear();
              //LazyInitializationException 예외 발생
//            System.out.println("findMember.getTeam() = " + findMember.getTeam());
              //프록시 초기화 확인
//            System.out.println(emf.getPersistenceUnitUtil().isLoaded(findMember)); //false
//            findMember.getUsername();
//            System.out.println(emf.getPersistenceUnitUtil().isLoaded(findMember)); //true

              //프록시 클래스 확인 방법
              //System.out.println(findMember.getClass().getName());

              //프록시 강제 초기화
              //org.hibernate.Hibernate.initialize(findMember);

            // ##### 즉시로딩과 지연로딩
//            Member findMember = em.find(Member.class, member.getId());
//            System.out.println("============================================");
//            //즉시로딩인 경우 Proxy객체가 아닌 진짜 객체를 가져옴
//            System.out.println("findMember = " + findMember.getTeam().getClass());
//            System.out.println("============================================");
//            //지연로딩인 경우 team를 가져와서 사용할 때 초기화
//            System.out.println("findMember.getTeam().getName() = " + findMember.getTeam().getName());

//            Parent parent = new Parent();
//            parent.setName("parent");
//
//            Child child1 = new Child();
//            child1.setName("child1");
//
//            Child child2 = new Child();
//            child2.setName("child2");
//            parent.addChild(child1);
//            parent.addChild(child2);
//            em.persist(parent);

//            em.persist(child1);
//            em.persist(child2);

//            em.flush();
//            em.clear();
//
//            Parent findParent = em.find(Parent.class, parent.getId());
//            findParent.getChildList().remove(0);

            //실전예제 5


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
