package jpql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * packageName : jpql
 * fileName : Product
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private int sockAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
