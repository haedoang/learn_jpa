package jpashop.domain;

import javax.persistence.*;

/**
 * packageName : jpashop.domain
 * fileName : OrderItem
 * author : haedoang
 * date : 2021/11/09
 * description :
 */
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;
    private Long orderPrice;
    private int count;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;
}
