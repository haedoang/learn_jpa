package jpashop.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * packageName : jpashop.domain
 * fileName : Book
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
@DiscriminatorValue("B")
@Entity
public class Book extends Item {
    private String author;
    private String isbn;
}
