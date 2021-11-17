package jpashop.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

/**
 * packageName : jpashop.domain
 * fileName : OrderItem
 * author : haedoang
 * date : 2021-11-15
 * description :
 */
@Entity
public class OrderItem extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;

    private int count;
}
