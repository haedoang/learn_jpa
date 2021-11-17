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

//            Address address = new Address("seoul", "songjung", "311");
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setHomeAddress(address);
//            em.persist(member);
//
//            Member member2 = new Member();
//            member2.setUsername("member2");
//            member2.setHomeAddress(address);
//            em.persist(member2);
//
//            member.getHomeAddress().setCity("newCity"); //값 타입이기 떄문에 member2도 업데이트 된다..

//            Address address = new Address("seoul", "songjung", "311");
//            Address address2 = new Address(address.getCity(), address.getStreet(), address.getZipcode());
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setHomeAddress(address);
//            em.persist(member);
//
//            Member member2 = new Member();
//            member2.setUsername("member2");
//            member2.setHomeAddress(address2);
//            em.persist(member2);
//
//            member.getHomeAddress().setCity("newCity"); //값 타입이기 떄문에 member2도 업데이트 된다..

            //불변 객체의 사용
//            Address address = new Address("seoul", "songjung", "311");
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setHomeAddress(address);
//            em.persist(member);
//
//            //값 자체를 새로 만든다.
//            Address address2 = new Address("newCity", address.getStreet(), address.getZipcode());
//            member.setHomeAddress(address2);


//            Member member = new Member();
//            member.setUsername("memberA");
//            member.setHomeAddress(new Address("homeCity", "street", "10000"));
//
//            member.getFavoriteFoods().add("치킨");
//            member.getFavoriteFoods().add("족발");
//            member.getFavoriteFoods().add("파스타");
//            member.getFavoriteFoods().add("라면");
//
//            member.getAddressHistory().add(new Address("old1", "street", "10000"));
//            member.getAddressHistory().add(new Address("old2", "street", "10000"));
//
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            System.out.println("================ START ===============");
//            //값 타입의 조회
//            Member findMember = em.find(Member.class, member.getId());
//
//            //기본적으로 지연 로딩이다.
//            findMember.getAddressHistory().forEach(System.out::println);
//            findMember.getFavoriteFoods().forEach(System.out::println);

            // homeCity -> newCity
            //findMember.getHomeAddress().setCity("newCity"); X
//            Address homeAddress = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", homeAddress.getStreet(), homeAddress.getZipcode()));

            //치킨 -> 한식
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");

            //old1 -> new1 ADDRESS 테이블을 다 지운 후 변경된 값들을 insert함
//            findMember.getAddressHistory().remove(new Address("old2", "street", "10000")); //equals 오버라이딩값 확인
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "10000"));

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
