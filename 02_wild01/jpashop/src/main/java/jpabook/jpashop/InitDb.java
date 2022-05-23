package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * packageName : jpabook.jpashop
 * fileName : InitDb
 * author : haedoang
 * date : 2021-12-03
 * description :
 */
@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "서울", "1", "1");
            em.persist(member);

            Book book1 = createBook("JPA1 book", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 book", 30000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = getDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery getDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String bookName, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(bookName);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }


        public void dbInit2() {
            Member member = createMember("userB", "부산", "122", "1333");
            em.persist(member);

            Book book = createBook("SPRING book1", 15000, 200);
            em.persist(book);

            Book book2 = createBook("SRPING book2", 35000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = getDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

    }
}
