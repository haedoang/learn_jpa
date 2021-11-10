package jpashop.domain;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * packageName : jpashop.domain.old2
 * fileName : Book
 * author : haedoang
 * date : 2021/11/10
 * description :
 */
@Entity
@DiscriminatorValue("B")
public class Book extends Item {
    private String author;
    private String isbn;
}
