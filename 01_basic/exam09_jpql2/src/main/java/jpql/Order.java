package jpql;

import javax.persistence.*;

/**
 * packageName : jpql
 * fileName : Order
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private long id;

    private int orderAmount;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

}
