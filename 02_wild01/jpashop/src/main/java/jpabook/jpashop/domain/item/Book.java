package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * packageName : jpabook.jpashop.domain
 * fileName : Book
 * author : haedoang
 * date : 2021/11/21
 * description :
 */
@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;

}
