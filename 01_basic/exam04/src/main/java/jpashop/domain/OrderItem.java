package jpashop.domain;

import jpashop.domain.base.BaseEntity;

import javax.persistence.*;

/**
 * packageName : jpashop.domain
 * fileName : OrderItem
 * author : haedoang
 * date : 2021/11/10
 * description :
 */
@Entity
public class OrderItem extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int count;
    private int orderPrice;

}
