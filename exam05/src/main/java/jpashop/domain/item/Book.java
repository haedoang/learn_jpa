package jpashop.domain.item;

import javax.persistence.Entity;

/**
 * packageName : jpashop.domain.item
 * fileName : Book
 * author : haedoang
 * date : 2021/11/14
 * description :
 */
@Entity
public class Book extends Item {
    private String author;
    private String isbn;
}
