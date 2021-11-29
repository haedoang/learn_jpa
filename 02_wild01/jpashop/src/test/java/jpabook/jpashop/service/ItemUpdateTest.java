package jpabook.jpashop.service;

import jpabook.jpashop.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

/**
 * packageName : jpabook.jpashop.service
 * fileName : ItemUpdatetEst
 * author : haedoang
 * date : 2021-11-29
 * description :
 */
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() {
        Book book = em.find(Book.class, 1L);

        // tx
        book.setName("asdasd");

        // 변경 감지
    }
}
