package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * packageName : jpabook.jpashop.repository
 * fileName : MemberRepository
 * author : haedoang
 * date : 2021/11/21
 * description :
 */
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;  //spring-data-jpa 에서 PersistenceContext를 Autowired로 의존성을 참조하게 지원한다. 따라서 RequiredArgsConstructor로 사용할 수 있음
//    @PersistenceContext
//    private EntityManager em;

//    @PersistenceUnit
//    private EntityManagerFactory factory;  : EntityManagerFactory 주입받는 방법

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
