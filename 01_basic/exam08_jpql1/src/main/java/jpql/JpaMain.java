package jpql;

import javax.persistence.*;
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

//            Member member = new Member();
//            member.setUsername("memberA");
//            member.setAge(33);
//            em.persist(member);

            //반환 타입이 명확할 때
            //TypedQuery<Member> query1 = em.createQuery("select m from Member m where m.id=10", Member.class);
            //TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            //반환타입이 명확하지 않을 때
            //Query query = em.createQuery("select m.username, m.age from Member m");

            //collection
            //query1.getResultList().forEach(System.out::println);
            //object
            //query1.getSingleResult().getClass(); //member 결과가 없을 떄 예외 발생

            //파라미터 바인딩 이름 기준
            //TypedQuery<Member> query3 = em.createQuery("select m from Member m where m.username = :username", Member.class);
            //query3.setParameter("username","memberA");
            ///Member singleResult = query3.getSingleResult();
            //System.out.println("singleResult = " + singleResult.getUsername());

            //파라미터 바인딩 위치 기준 비추천..
            //TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.username = ?1", Member.class);
            //query4.setParameter(1, "memberA");

            //em.flush();
            //em.clear();

            //영속성 컨텍스트에 관리 대상임.
            //TypedQuery<Member> query = em.createQuery("select  m from Member m", Member.class);
            //List<Member> resultList = query.getResultList();

            //Member findMember = resultList.get(0);
            //findMember.setAge(20);

            //em.createQuery("select  m.team from Member m", Team.class) Team 객체를 가져오지만 유지보수성이 떨어진다..  join으로 가져오기
            //List<Team> result = em.createQuery("select  m.team from Member m join m.team t", Team.class).getResultList();

            //임베디드 타입 프로젝션
            //em.createQuery("select o.address from Order o", Address.class).getResultList();

            //스칼라 타입 프로젝션
            //em.createQuery("select distinct m.username, m.age from Member m").getResultList();


            //1. Query타입으로 조회
//            List resultList = em.createQuery("select m.username, m.age from Member m").getResultList();
//
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//            System.out.println("result[0] = " + result[0]);
//            System.out.println("result[1] = " + result[1]);
            //2. Object[] 타입으로 조회
//            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m").getResultList();
//            Object[] result = resultList.get(0);
//            System.out.println("result[0] = " + result[0]);
//            System.out.println("result[1] = " + result[1]);

            //3. new 명령어로 조회
            //단순 값을 DTO로 바로 조회
//            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
//            MemberDTO memberDTO = resultList.get(0);
//            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
//            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());

//            IntStream.range(0, 100).forEach(i -> {
//                Member m = new Member();
//                m.setAge(i);
//                m.setUsername("name" + i);
//                em.persist(m);
//            });

            //페이징
//            for(int i = 0 ; i < 100; i++){
//                Member member = new Member();
//                member.setAge(i);
//                member.setUsername("member" + i);
//                em.persist(member);
//            }
//
//            em.flush();
//            em.clear();
//
//
//            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//            System.out.println("resultList.size() = " + resultList.size());
//            for (Member member : resultList) {
//                System.out.println("member = " + member);
//            }

//            Team team = new Team();
//            team.setName("teaMA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setAge(33);
//
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            //String query =  "select m from Member m join m.team t"; inner join inner 생략ㄱ ㅏ능
//            //String query =  "select m from Member m left join m.team t"; //outer 생략 가능
//            String query = "select m from Member m, Team t where m.username = t.name"; // 세타 조인 (크로스 조인)
//            List<Member> resultList = em.createQuery(query, Member.class).getResultList();



            //조건식
            Member m = new Member();
//            m.setUsername("memberA");
            m.setUsername("관리자");
            m.setAge(33);
            m.setType(MemberType.USER);
            em.persist(m);

//            String query = "select " +
//                            "case when m.age <= 10 then '학생요금'" +
//                            " when m.age >= 60 then '경로요금'" +
//                            " else '일반요금'" +
//                            "end" +
//                    " from Member m";
//            String s = em.createQuery(query, String.class).getResultList().get(0);
//            System.out.println("s = " + s);
            // null이 아니면 반환 ifnull 같음.
//            String query = "select coalesce(m.username, '이름 없는 회원') as username from Member m";
            //두값이 같으면 null 반환 다르면 첫번쨰 값 반환
            String query = "select nullif(m.username, '관리자') as username from Member m";
            String result = em.createQuery(query, String.class).getResultList().get(0);
            System.out.println("result = " + result);
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
